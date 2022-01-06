package dataStructure.LinkedList;

/*链表节点*/
public class ListNode {
    int val;
    ListNode next;

    /*构造函数*/
    ListNode(){}
    ListNode(int val){
        this.val = val;
    }
    ListNode(int val, ListNode next) {
        this.val= val;
        this.next=next;
    }

}
