/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 5
 * Date Assigned: 4/18/2015
 * Date Due: 4/29/2015
 * Date Submitted: 4/29/2015
 ***********************************/

package algoData;


/*************************************************************************
 *  Compilation:  javac Huffman.java
 *  Execution:    java Huffman - < input.txt   (compress)
 *  Execution:    java Huffman + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   http://algs4.cs.princeton.edu/55compression/abra.txt
 *                http://algs4.cs.princeton.edu/55compression/tinytinyTale.txt
 *
 *  Compress or expand a binary input stream using the Huffman algorithm.
 *
 *  % java Huffman - < abra.txt | java BinaryDump 60
 *  010100000100101000100010010000110100001101010100101010000100
 *  000000000000000000000000000110001111100101101000111110010100
 *  120 bits
 *
 *  % java Huffman - < abra.txt | java Huffman +
 *  ABRACADABRA!
 *
 *************************************************************************/

public class HuffTree {

    // alphabet size of extended ASCII
    private static final int R = 256;

    // compress bytes from standard input and write to standard output
    public static void compress() {
        // read the input
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        // tabulate frequency counts
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++)
            freq[input[i]]++;

        // build Huffman trie
        HuffNode root = buildTrie(freq);

        // build code table
        String[] st = new String[R];
        buildCode(st, root, "");

        // print trie for decoder
        writeTrie(root);

        // print number of bytes in original uncompressed message
        BinaryStdOut.write(input.length);

        // use Huffman code to encode input
        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '0') {
                    BinaryStdOut.write(false);
                }
                else if (code.charAt(j) == '1') {
                    BinaryStdOut.write(true);
                }
                else throw new IllegalStateException("Illegal state");
            }
        }

        // close output stream
        BinaryStdOut.close();
    }

    // build the Huffman trie given frequencies
    private static HuffNode buildTrie(int[] freq) {

        // initialze priority queue with singleton trees
        MinPQ<HuffNode> pq = new MinPQ<HuffNode>();
        for (char i = 0; i < R; i++)
            if (freq[i] > 0)
                pq.insert(new HuffNode(i, freq[i], null, null));

        // special case in case there is only one character with a nonzero frequency
        if (pq.size() == 1) {
           if (freq['\0'] == 0) pq.insert(new HuffNode('\0', 0, null, null));
           else                 pq.insert(new HuffNode('\1', 0, null, null));
        }

        // merge two smallest trees
        while (pq.size() > 1) {
            HuffNode left  = pq.delMin();
            HuffNode right = pq.delMin();
            HuffNode parent = new HuffNode('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }


    // write bitstring-encoded trie to standard output
    private static void writeTrie(HuffNode x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch, 8);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    // make a lookup table from symbols and their encodings
    private static void buildCode(String[] st, HuffNode x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left,  s + '0');
            buildCode(st, x.right, s + '1');
        }
        else {
            st[x.ch] = s;
        }
    }


    // expand Huffman-encoded input from standard input and write to standard output
    public static void expand() {

        // read in Huffman trie from input stream
        HuffNode root = readTrie(); 

        // number of bytes to write
        int length = BinaryStdIn.readInt();

        // decode using the Huffman trie
        for (int i = 0; i < length; i++) {
            HuffNode x = root;
            while (!x.isLeaf()) {
                boolean bit = BinaryStdIn.readBoolean();
                if (bit) x = x.right;
                else     x = x.left;
            }
            BinaryStdOut.write(x.ch, 8);
        }
        BinaryStdOut.close();
    }


    private static HuffNode readTrie() {
        boolean isLeaf = BinaryStdIn.readBoolean();
        if (isLeaf) {
            return new HuffNode(BinaryStdIn.readChar(), -1, null, null);
        }
        else {
            return new HuffNode('\0', -1, readTrie(), readTrie());
        }
    }


    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}