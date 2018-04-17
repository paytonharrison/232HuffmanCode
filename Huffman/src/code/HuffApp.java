/*
Authors: Tyler Osterhues, Zachary Taylor, Payton Harrison
Course: CSCI 232
Version: v3.9.17
Last Updated: September 22, 2017
Description: The HuffApp class reads input from a text file and encodes and
decodes it using a Huffman Tree.
*/

package code;

import java.io.BufferedReader;

import java.io.File;        //We used these two imports to read the text file
import java.io.FileReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;


public class HuffApp {
        private int[]freqTable;
        private final static int ASCII_TABLE_SIZE = 128;
        private String originalMessage = "";
        private PriorityQ theQueue;
        private HuffTree huffTree;
        private String encodedMessage = "";
        private String[] codeTable;
        private String decodedMessage = "";


        public static void main(String[] args) {
                new HuffApp();
        }


        public HuffApp() {
                codeTable = new String[ASCII_TABLE_SIZE];
                readInput();
                displayOriginalMessage();
                makeFrequencyTable(originalMessage);
                displayFrequencyTable();
                addToQueue();
                buildTree(theQueue);
                makeCodeTable(huffTree.root, "");
                encode();
                displayEncodedMessage();
                displayCodeTable();
                decode();
                displayDecodedMessage();
        }

        private void readInput() {
                //read input in from the input.txt file and save to originalMessage     field
               //String content = new String(Files.readAllBytes(Paths.get("input.txt")));
               try{
                   File file = new File("src/code/input.txt"); //finds text file named 'input' in 'code' package
                   FileReader fr = new FileReader(file);
                   BufferedReader br = new BufferedReader(fr);
                   String current;
                   while((current = br.readLine()) != null){
                       originalMessage += current;
                   }
                   originalMessage = originalMessage.toLowerCase();
               }catch(Exception e){
                   System.out.println("Das ist nicht gut");
                   System.out.println();
                   e.printStackTrace();
                   System.out.println();
               }

        }

        private void displayOriginalMessage() {
                System.out.println("Original message: " + originalMessage);
                System.out.println();
        }

        private void makeFrequencyTable(String inputString)
        {
                //populate the frequency table using inputString. results are saved to the
                //freqTable field
           freqTable = new int[27];
           int count;
           int index = 0;
           for(char ch = 'a'; ch <= 'z'; ch++){
               count = 0;
               for(int i = 0; i < inputString.length(); i++){
                   if(ch == inputString.charAt(i)){
                       count++;
                   }
               }
               freqTable[index] = count;
               index ++;
           }
           count = 0;
           for(int i = 0; i < inputString.length(); i++){
               if(inputString.charAt(i) == ' '){
                   count++;
               }
           }
           freqTable[index] = count;
        }

        private void displayFrequencyTable()
        {
                //print the frequency table. skipping any elements that are not represented
            System.out.println("Frequency Table");
            System.out.println("char | val");
            int i = 0;
            if(freqTable[26] != 0){
               System.out.println("     | " + freqTable[26]);
            }
            for(char ch = 'a'; ch <= 'z'; ch++){
               if(freqTable[i] != 0){
                   System.out.println(ch + "    | " + freqTable[i]);
               }
               i++;
            }

        }

        private void addToQueue()
        {
                //add the values in the frequency table to the PriorityQueue. Hint use the
                //PriorityQ class. save the results to theQueue field
           theQueue = new PriorityQ(27);
           int i = 0;
           for(char ch = 'a'; ch <= 'z'; ch++){
               if(freqTable[i] > 0){
                   theQueue.insert(new HuffTree(ch, freqTable[i]));
               }
               i++;
           }
           if(freqTable[i] > 0){
               theQueue.insert(new HuffTree(' ', freqTable[i]));
           }
        }

        private void buildTree(PriorityQ hufflist)
        {
                //pull items from the priority queue and combine them to form
                //a HuffTree. Save the results to the huffTree field
            HuffTree itemOne = null;
            while(!theQueue.isEmpty()){
                itemOne = theQueue.remove();
                if(!theQueue.isEmpty()){
                    HuffTree itemTwo = theQueue.remove();
                    HuffTree newItem = new HuffTree(itemOne.getWeight()+itemTwo.getWeight(), itemOne, itemTwo);
                    theQueue.insert(newItem);
                }
            }
            huffTree = itemOne;
        }

        private void makeCodeTable(HuffNode huffNode, String bc)
        {
                //hint, this will be a recursive method
            if(huffNode.leftChild != null){
                makeCodeTable(huffNode.leftChild, bc+"0");
            }
            if(huffNode.rightChild != null){
                makeCodeTable(huffNode.rightChild, bc+"1");
            }
            if(huffNode.isLeaf()){
                    codeTable[(int)huffNode.character] = bc;

            }
        }

        private void displayCodeTable()
        {
                //print code table, skipping any empty elements
            System.out.println("Code Table");
            System.out.println("char | val");
            for(int i = 0; i < codeTable.length; i++){
                if(codeTable[i] != null){
                    System.out.println((char)i + "    | " + codeTable[i]);
                }
            }
        }

        private void encode()
        {
                //use the code table to encode originalMessage. Save result in the encodedMessage field
            for(char ch : originalMessage.toCharArray()){
                encodedMessage += codeTable[(int)ch];
            }
        }

        private void displayEncodedMessage() {
                System.out.println("\nEncoded message: ");
                System.out.println(encodedMessage);
                System.out.println();
        }

        private void decode()
        {
                //decode the message and store the result in the decodedMessage field
            HuffNode node = huffTree.root;
            for(int i = 0; i < encodedMessage.length(); i++){
                if(encodedMessage.charAt(i) == '0' && !node.isLeaf()){
                    node = node.leftChild;
                }
                if(encodedMessage.charAt(i) == '1' && !node.isLeaf()){
                    node = node.rightChild;
                }
                if(node.isLeaf()){
                    decodedMessage += node.character;
                    node = huffTree.root;
                }
            }
        }

        public void displayDecodedMessage() {
                System.out.println("\nDecoded message: " + decodedMessage);
        }

}