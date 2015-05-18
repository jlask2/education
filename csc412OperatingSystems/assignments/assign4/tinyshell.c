/* ******************************************************
 * Author@ Jason Laske 
 * Date Due: 5/5/2015
 * Date Submitted: 5/5/2015
 * Description: tsh - A tiny shell program with only 4
 * built in commands - newcat, world [int argv],
 * mars [int argv] and quit.
 *******************************************************/

/* Headers */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <ctype.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <errno.h>

/* Defined constants */
#define MAXLINE    1024   /* max line size */
#define MAXARGS     128   /* max args on a command line */

/* Global variables */
extern char **environ;      /* defined in libc */
char prompt[] = "next command> ";    /* command line prompt */
char sbuf[MAXLINE];         /* for composing sprintf messages */
int status;		    /* for returning status from wait */
pid_t pid;		    /* variable for process id's and states*/

/* Function prototypes */

/* eval function */
void eval(char *cmdline);

/* builtin_cmd function */
int builtin_cmd(char **argv);

/* parseline function */
int parseline(const char *cmdline, char **argv); 

/* run_child function */
void run_child(char **argv);


/* Utility Functions*/

void usage(void);
void unix_error(char *msg);
pid_t Fork(void);
void app_error(char *msg);

/*
 * main - The shell's main routine 
 */
int main(int argc, char **argv) 
{
    char c;
    char cmdline[MAXLINE];
    int emit_prompt = 1; /* emit prompt (default) */

    /* Execute the shell's read/eval loop */
    while (1) {

	/* Read command line */
	if (emit_prompt) {
	    printf("%s", prompt);                   
	    fflush(stdout);
	}
	if ((fgets(cmdline, MAXLINE, stdin) == NULL) && ferror(stdin))
	    app_error("fgets error");
	if (feof(stdin)) { /* End of file (ctrl-d) */
	    fflush(stdout);
	    exit(0);
	}

	/* Evaluate the command line */
	eval(cmdline);
	fflush(stdout);
	fflush(stderr);
    } 
    exit(0); /* control never reaches here */
}
  
/* 
 * eval - Evaluate the command line that the user has just typed in
 * 
 * If the user has requested a built-in command (quit, world, mars, newcat)
 * then execute it immediately. Parent waits for the child to terminate.
*/
void eval(char *cmdline) 
{
	char *argv[MAXARGS]; 		// argv for execve() 

	parseline(cmdline, argv);

	if (argv[0] == NULL)
		return; // ignore empty lines 

	if(!builtin_cmd(argv)){ 
		printf("Not a valid command\n");
	}else{
		pid = wait(&status);
	}		
	return;
}

/* 
 * parseline - Parse the command line and build the argv array.
 */
int parseline(const char *cmdline, char **argv) 
{
    static char array[MAXLINE]; /* holds local copy of command line */
    char *buf = array;          /* ptr that traverses command line */
    char *delim;                /* points to first space delimiter */
    int argc;                   /* number of args */
    //int bg;                     /* background job? */

    strcpy(buf, cmdline);
    buf[strlen(buf)-1] = ' ';       /* replace trailing '\n' with space */
    while (*buf && (*buf == ' ')) { /* ignore leading spaces */
	buf++;
    }

    /* Build the argv list */
    argc = 0;
    while ((delim = strchr(buf, ' '))) {
	argv[argc++] = buf;
	*delim = '\0';
	buf = delim + 1;
	while (*buf && (*buf == ' ')) /* ignore spaces */
	       buf++;
    }

    argv[argc] = NULL;
    
    if (argc == 0)  /* ignore blank line */
	return 1;

    return 0;
}

/* 
 * builtin_cmd - If the user has typed a built-in command then execute
 *    it immediately.  
 */
int builtin_cmd(char **argv) 
{
	if(!strcmp(argv[0], "quit")){  /* quit command */
		exit(0);
	}
	if(!strcmp(argv[0], "world")){ /* world command */
		run_child(argv);
		return 1;
	}
	if(!strcmp(argv[0], "mars")){ /* mars command */
		run_child(argv);		
		return 1;
	}
	if(!strcmp(argv[0], "newcat")){ /* newcat command */
		run_child(argv);
		return 1;
	}
	return 0;     		  /* not a builtin command */
}

/*
 * run_child - if child was successfully forked execute the given command
 */
void run_child(char **argv)
{
	if ((pid = Fork()) == 0) { // child runs user job 
		if(execve(argv[0], argv, environ) < 0){
			printf("%s: Command not found.\n", argv[0]);
			exit(0);
		}
	}
}



/*
 * unix_error - unix-style error routine
 */
void unix_error(char *msg)
{
    fprintf(stderr, "%s: %s\n", msg, strerror(errno));
    exit(1);
}

/*
 * Fork - wrapper function for fork
 */
pid_t Fork(void)
{
  pid_t pid;

  if((pid=fork())<0)
    unix_error("Fork error");
  return pid;
}

/*
 * app_error - application-style error routine
 */
void app_error(char *msg)
{
    fprintf(stderr, "%s\n", msg);
    exit(1);
}

