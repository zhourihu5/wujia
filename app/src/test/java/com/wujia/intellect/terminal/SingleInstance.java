package com.wujia.intellect.terminal;

public class SingleInstance {

    private static SingleInstance instance;
    private SingleInstance(){

    }
    public static SingleInstance getInstance(){
        if(instance==null){
            synchronized (SingleInstance.class){
                if(instance==null){
                    instance=new SingleInstance();
                }
            }
        }
        return instance;
    }
    static class Node{
        Node next;
        int value;
        public Node(int value){
            this.value=value;
        }
    }
    public static Node reverseList(Node node){
        Node result=null;
        Node pre=null;
        Node current=node;
        while (current!=null){
            Node next=current.next;
            current.next=pre;
            pre=current;
            result=current;
            current=next;
        }


        return result;
    }

    public static void main(String[] args) {

        Node node1=new Node(1);
        Node node2=new Node(2);
        Node node3=new Node(3);
        Node node4=new Node(4);

        node1.next=node2;
        node2.next=node3;
        node3.next=node4;

        Node rList= reverseList(node1);
        while (rList!=null){
            System.out.println(rList.value);
            rList=rList.next;
        }

    }

}
