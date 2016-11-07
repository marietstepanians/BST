package com.company;

import com.company.dataModel.BinaryTree;

import java.util.Scanner;

public class Main {
    private static BinaryTree<Integer> myTree;

    public static void main(String[] args) {
        myTree = new BinaryTree<>();
        printInstructions();
        int i = 0;
        Scanner scanner = new Scanner(System.in);
        while (i != -1){
            i = scanner.nextInt();
            switch (i){
                case 1:
                    insertNodes();
                    break;
                case 2:
                    deleteNode();
                    break;
                case 3:
                    findNode();
                    break;
                case 4:
                    System.out.println("Height of three is " + myTree.getHeight());
                    System.out.print("Please insert command_ ");
                    break;
                case 5:
                    System.out.println("In order traverse through three");
                    myTree.inOrderTraverse();
                    System.out.print("Please insert command_ ");
                    break;
                case 6:
                    System.out.println("Pre-order traverse through three");
                    myTree.preOrderTraverse();
                    System.out.print("Please insert command_ ");
                    break;
                case 7:
                    System.out.println("Post-order traverse through three");
                    myTree.postOrderTraverse();
                    System.out.print("Please insert command_ ");
                    break;
                case 100:
                    System.out.println();
                    printInstructions();
                    break;
                case -1:
                    break;
                default:
                    System.out.println("wrong command please try again");

            }
        }
    }

    private static void printInstructions(){
        System.out.println("Please use above commands to work with with Tree");
        System.out.println("press 1 to add node to three");
        System.out.println("press 2 to delete node from three");
        System.out.println("press 3 to find if node exists in tree or note");
        System.out.println("press 4 to get height of tree");
        System.out.println("press 5 for in-order traverse through three");
        System.out.println("press 6 for pre-order traverse through three");
        System.out.println("press 7 for post-order traverse through three");
        System.out.println("press 100 to print instructions");
        System.out.println("press -1 to finish");
        System.out.print("Please insert command_ ");
    }

    private static void insertNodes(){
        int i = 0;
        System.out.println("Insert number of nodes to add");
        Scanner scanner = new Scanner(System.in);
        i = scanner.nextInt();
        System.out.println("Insert " + i +" nodes.");
        System.out.println("Tree can not contain duplicate values");
        for(int j = 0; j< i; j++){
                myTree.insert(scanner.nextInt());
        }
        System.out.println("Nodes are added");
        System.out.print("Please insert other command_ ");
    }

    private static void deleteNode(){
        int i = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert number of nodes to delete");
        i = scanner.nextInt();
        while (i > 0){
            System.out.println("Insert value of node to delete");
            int j = scanner.nextInt();
                boolean isDeleted = myTree.remove(j);
                if(isDeleted){
                    System.out.println("Node successfully removed");
                }
                else {
                    System.out.println("inserted node does not exists");
                }
            i--;
        }
        System.out.print("Please insert other command_ ");
    }

    private static void findNode(){
        int k;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert value of node to search");
        k = scanner.nextInt();
        if(myTree.find(k)){
            System.out.println("Three contains node with given value");
        }
        else {
            System.out.println("Three does not contains node with given value");
        }
        System.out.print("Please insert other command_ ");
    }
}
