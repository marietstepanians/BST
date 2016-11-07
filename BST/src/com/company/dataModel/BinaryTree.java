package com.company.dataModel;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariet on 11/6/16.
 */
/** Binary search tree data structure
    All methods are using iterative analogs for BST recursive algorithms to avoid StackOverflow exception for large Tree **/
public class BinaryTree<T extends Comparable> {
    /** Root element of tree **/
    private Node<T> root;

    /** Insert Node to tree
     *  Throws duplicate node exception if node is repeated **/
    public boolean insert(T value) {
        if(value == null){
            return false;
        }
        Node<T> node = new Node<>(value);
        if(root == null){
            root = node;
            return true;
        }
        else {
            Node current = getNodeToAdd(node);
            return addNodeToParent(current, node);
        }
    }

    /** Helper methods for insert() method **/
    private boolean addNodeToParent(Node<T> parent, Node<T> node){
        if(parent == null){
            return  false;
        }
        node.parent = parent;
        if(node.value.compareTo(parent.value) < 0){
            parent.leftNode = node;
        }
        else {
            parent.rightNode = node;
        }
        return true;
    }

    /** Find place to add new node **/
    private Node<T> getNodeToAdd(Node<T> node) {
        Node current = root;
        boolean isRightPlace = false;
        while (!isRightPlace){
            if(node.value.compareTo(current.value) < 0){
                if(current.leftNode != null){
                    current = current.leftNode;
                    continue;
                }
                else {
                    return current;
                }
            }
            if(node.value.compareTo(current.value) > 0){
                if(current.rightNode != null){
                    current =  current.rightNode;
                    continue;
                }
                else {
                    return current;
                }
            }
            if(node.value.compareTo(current.value) == 0){
                return null;
            }
        }
        return current;
    }

    /** Returns true if node with given value exists snd false if node with given value did note exists **/
    public boolean find(T value){
        if(findNode(value) != null){
            return true;
        }
        else {
            return false;
        }
    }

    /** gives node with given value if exists else gives null **/
    private Node<T> findNode(T value){
        Node<T> current = root;
        while (current != null){
            if(value.compareTo(current.value) < 0){
                current = current.leftNode;
                continue;
            }
            if(value.compareTo(current.value) > 0){
                current = current.rightNode;
                continue;
            }
            if(value.compareTo(current.value) == 0){
                return current;
            }
        }
        return current;
    }

    /** Remove Node with given value if exists
        returns true if node exists and false if not exists **/
    public boolean remove(T value) {
        if(root == null){
            return false;
        }
        Node<T> nodeToDelete = findNode(value);
        if(nodeToDelete == null){
            return false;
        }
        if(nodeToDelete.rightNode == null || nodeToDelete.leftNode == null){
            return deleteAsymmetricNode(nodeToDelete);
        }
        else {
            return deleteSymmetricNode(nodeToDelete);
        }
    }

    /** delete node with 2 child's **/
    public boolean deleteSymmetricNode(Node<T> nodeToDelete){
        Node<T> smallestRightNode = findSmallestRightChild(nodeToDelete.rightNode);
        smallestRightNode.parent.leftNode = smallestRightNode.rightNode;
        smallestRightNode.leftNode = nodeToDelete.leftNode;
        smallestRightNode.rightNode = nodeToDelete.rightNode;
        if(nodeToDelete.leftNode != null){
            nodeToDelete.leftNode.parent = smallestRightNode;
        }
        if(nodeToDelete.rightNode != null){
            nodeToDelete.rightNode.parent = smallestRightNode;
        }
        if(nodeToDelete.parent != null) {
            if (nodeToDelete.equals(nodeToDelete.parent.rightNode)) {
                nodeToDelete.parent.rightNode = smallestRightNode;
                return true;
            }
            if (nodeToDelete.equals(nodeToDelete.parent.leftNode)) {
                nodeToDelete.parent.leftNode = smallestRightNode;
                return true;
            }
        }
        else {
            root = smallestRightNode;
            root.leftNode = nodeToDelete.leftNode;
            root.rightNode = nodeToDelete.rightNode;
            root.parent = null;
        }
        return true;
    }


    /** deletes node with one child or with no child **/
    private boolean deleteAsymmetricNode(Node<T> nodeToDelete){
        boolean isRight;
        if(nodeToDelete.equals(nodeToDelete.parent.rightNode)){
            isRight = true;
        }
        else {
            isRight = false;
        }
        Node<T> child = null;
        if(nodeToDelete.rightNode != null){
            child = nodeToDelete.rightNode;
        }
        if(nodeToDelete.leftNode != null){
            child = nodeToDelete.leftNode;
        }
        if(isRight){
            nodeToDelete.parent.rightNode = child;
        }
        else {
            nodeToDelete.parent.leftNode = child;
        }
        if(child != null){
            child.parent = nodeToDelete.parent;
        }
        return true;
    }

    /** finds Smallest Child ing right subtree **/
    public Node<T> findSmallestRightChild(Node<T> node){
        Node<T> current = node;
        while(current.leftNode != null) {
            current = current.leftNode;
        }
        return current;
    }

    /** get height of tree **/
    public int getHeight(){
        int maxHeight = 0;
        List<Node<T>> visitedNodes = new ArrayList<>();
        Node<T> currentNode = root;
        visitedNodes.add(currentNode);
        while (currentNode != null){
            if(!visitedNodes.contains(currentNode)){
                visitedNodes.add(currentNode);
                if(maxHeight <= currentNode.height) {
                    maxHeight = currentNode.height;
                }
            }
            if(currentNode.leftNode != null && !visitedNodes.contains(currentNode.leftNode)){
                currentNode = currentNode.leftNode;
                currentNode.height = currentNode.parent.height + 1;
                continue;
            }
            else if(currentNode.rightNode != null && !visitedNodes.contains(currentNode.rightNode)){
                currentNode = currentNode.rightNode;
                currentNode.height = currentNode.parent.height + 1;
                continue;
            }
            else {
                currentNode = currentNode.parent;
                continue;
            }
        }
        return maxHeight;
    }

    /** Traverse Methods **/
    public void inOrderTraverse(){
        Node<T> currentNode = root;
        List<Node<T>> printedNodes = new ArrayList<>();
        while (currentNode != null){
            if(currentNode.leftNode == null || printedNodes.contains(currentNode.leftNode)){
                if(!printedNodes.contains(currentNode)) {
                    System.out.println(currentNode.value);
                    printedNodes.add(currentNode);
                }
                if(currentNode.rightNode != null && !printedNodes.contains(currentNode.rightNode)){
                    currentNode = currentNode.rightNode;
                    continue;
                }
                else {
                    currentNode = currentNode.parent;
                    continue;
                }
            }
            else {
                currentNode = currentNode.leftNode;
                continue;
            }

        }
    }

    public void preOrderTraverse(){
        List<Node<T>> printedNodes = new ArrayList<>();
        Node<T> currentNode = root;
        while(currentNode != null) {
            if(!printedNodes.contains(currentNode)){
                System.out.println(currentNode.value);
                printedNodes.add(currentNode);
            }
            if(currentNode.leftNode != null && !printedNodes.contains(currentNode.leftNode)){
                currentNode = currentNode.leftNode;
                continue;
            }
            else if(currentNode.rightNode != null && !printedNodes.contains(currentNode.rightNode)){
                currentNode = currentNode.rightNode;
                continue;
            }
            else {
                currentNode = currentNode.parent;
                continue;
            }
        }
    }

    public void postOrderTraverse(){
        List<Node<T>> printedNodes = new ArrayList<>();
        Node<T> currentNode = root;
        while (currentNode != null) {
            if(!printedNodes.contains(currentNode) && (currentNode.leftNode == null || printedNodes.contains(currentNode.leftNode))
                    && (currentNode.rightNode == null || printedNodes.contains(currentNode.rightNode))){
                System.out.println(currentNode.value);
                printedNodes.add(currentNode);
            }
            if(currentNode.leftNode != null && !printedNodes.contains(currentNode.leftNode)){
                currentNode = currentNode.leftNode;
                continue;
            }
            else if(currentNode.rightNode != null && !printedNodes.contains(currentNode.rightNode)){
                currentNode = currentNode.rightNode;
                continue;
            }
            else {
                currentNode = currentNode.parent;
                continue;
            }
        }
    }

    /** Upper bound for Node class is Comparable interface only data Types with this interface can.
        be used to create Tree.
        This is done because We need to compare elements to find right place for node.
        We are using Node<T> type for references because in Java variables have strong references to objects.
        do not use WeakReference because Weak reference will not keep object from GC.
        do not use SoftReference because object will be lost when tree grows bigger. **/
    private class Node< T extends Comparable>{
        /** parent object of node **/
        public Node<T> parent;
        /** left child of node **/
        public Node<T> leftNode;
        /** right child of node **/
        public Node<T> rightNode;
        /** height of current node is used to calculate height of tree **/
        public int height = 0;
        /** value of node **/
        T value;

        /** constructor **/
        public Node(T value){
            this.value = value;
        }
    }
}
