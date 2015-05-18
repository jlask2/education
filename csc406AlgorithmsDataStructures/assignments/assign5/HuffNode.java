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

/** Class HuffNode: Huffman Node for the implementation of a Huffman Tree */
//Huffman tree node
class HuffNode implements Comparable<HuffNode> {
    protected final char ch;
    protected final int freq;
    protected final HuffNode left, right;

    HuffNode(char ch, int freq, HuffNode left, HuffNode right) {
        this.ch    = ch;
        this.freq  = freq;
        this.left  = left;
        this.right = right;
    }

    // is the HuffNode a leaf node?
    protected boolean isLeaf() {
        assert (left == null && right == null) || (left != null && right != null);
        return (left == null && right == null);
    }

    // compare, based on frequency
    public int compareTo(HuffNode that) {
        return this.freq - that.freq;
    }
}
