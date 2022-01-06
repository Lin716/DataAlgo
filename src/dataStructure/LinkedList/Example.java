package dataStructure.LinkedList;

import javax.swing.event.ListDataEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/*关于链表的例题*/
public class Example {

    /*1.通过设置头节点来统一处理问题*/

    /*例题1：合并有序链表*/
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                cur.next = l1;
                l1 = l1.next;

            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        //拼接剩下的部分
        cur.next = l1 != null ? l1 : l2;
        return dummy.next;

    }

    /*链表数组合并*/
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;

        //用优先队列来存储每个链表的头结点
        PriorityQueue<ListNode> queue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });

        for (ListNode node : lists) {
            if (node != null) {
                queue.offer(node);
            }
        }

        while (!queue.isEmpty()) {
            ListNode node = queue.poll();
            cur.next = node;
            cur = cur.next;

            //拼接的这节点的下一个也加入队列
            node = node.next;
            if (node != null) queue.offer(node);
        }

        return dummy.next;

    }


    /*给你两个链表 list1 和 list2 ，它们包含的元素分别为 n 个和 m 个。

    请你将 list1 中下标从 a 到 b 的全部节点都删除，并将list2 接在被删除节点的位置。*/

    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {

        ListNode nodeA = list1;
        //找到第a-1个节点
        for (int i = 0; i < a - 1; i++) {
            nodeA = nodeA.next;
        }
        //找到第b+1个节点
        ListNode nodeB = nodeA.next;
        for (int i = a; i < b; i++) {
            nodeB = nodeB.next;
        }
        nodeB = nodeB.next;

        //然后拼接list2
        ListNode tail = list2;

        //找到list2 的尾结点
        while (tail.next != null) {
            tail = tail.next;
        }

        nodeA.next = list2;
        tail.next = nodeB;
        return list1;
    }

    /*链表排序:将链表变为有序的。归并排序*/
    public ListNode sortList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }
        //第一步：将链表分为两个,快慢指针拆分
        ListNode prev = head, slow = head, fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        //前面链表的尾结点
        prev.next = null;

        //第二步：将两部分的链表分别排序
        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);

        //第三步：合并两个有序链表
        return merge(l1, l2);
    }

    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode p = new ListNode();
        ListNode l = p;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }
        p.next = l1 != null ? l1 : l2;
        return l.next;
    }


    /*翻转链表*/
    public ListNode reverseList(ListNode head) {
        //一共由三个：prev，cur，next
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    /*翻转固定区间的*/
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode prev = dummy;
        //1.找到left的上一个节点
        for (int i = 0; i < left - 1; i++) {
            prev = prev.next;
        }
        //2。找到尾结点
        ListNode rightNode = prev;
        for (int i = 0; i < right - left + 1) {
            rightNode = rightNode.next;
        }

        //3.切出中间的部分
        ListNode leftNode = prev.next;
        ListNode curr = rightNode.next;

        prev.next = null;
        rightNode.next = null;

        //4.翻转切出的
        reverseList2(leftNode);

        //5.接回原来的链表
        prev.next = rightNode;
        leftNode.next = curr;
        return dummy.next;
    }

    public void reverseList2(ListNode head) {
        //一共由三个：prev，cur，next
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
    }

    /*两两交换*/
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode temp = head.next.next;
            prev.next = head.next;
            head.next.next = head;
            head.next = temp;

            prev = head;
            head = head.next;
        }
        return dummy.next;
    }


    /*删除链表节点*/
    public ListNode removeKthFromEnd(ListNode head, int k) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode slow = dummy;
        ListNode fast = dummy;
        while (k-- > 0) {
            fast = fast.next;
        }
        ListNode prev = null;
        while (fast != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next;
        }
        prev.next = slow.next;
        slow.next = null;
        return dummy.next;

    }


    /*奇偶链表：给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。*/
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return head;
        }
        /*维护奇偶链表的头结点，然后将偶链表拼接在奇链表的后面*/
        ListNode oddHead = head;
        ListNode evenHead = head.next;

        //奇偶指针指向奇数节点和偶数节点，然后每次更新奇数节点，然后更新偶数节点
        ListNode odd = head, even = head.next;

        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        //偶数链表拼接在后面
        odd.next = even;
        return head;
    }

    /*重排链表：L0 → L1 → … → Ln - 1 → Ln
     * L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …*/

    /*采用线性表存储来随机访问，*/
    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        List<ListNode> list = new ArrayList<>();
        ListNode node = head;
        while (node != null) {
            list.add(node);
            node = node.next;
        }

        int i = 0, j = list.size() - 1;

        while (i < j) {
            list.get(i).next = list.get(j);
            i++;
            if (i == j) {
                break;
            }
            list.get(j).next = list.get(i);
            j--;

        }
        list.get(i).next = null;
    }

    /*环形链表*/
    public boolean hasCycle(ListNode head) {
        //特殊情况
        if (head == null || head.next == null) {
            return false;
        }
        //快慢指针
        ListNode slow = head;
        ListNode fast = head.next;

        //有环的话快慢指针终会相遇，快指针移动两步，慢指针移动一步
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /*返回入环的节点*/
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                //有环
                ListNode index1 = fast;
                ListNode index2 = head;
                while (index1 != index2) {
                    index1 = index1.next;
                    index2 = index2.next;
                }
                return index1;
            }
        }
        return null;
    }

    /*链表求和*/
    /*给定两个用链表表示的整数，每个节点包含一个数位。*/
    /*输入：(7 -> 1 -> 6) + (5 -> 9 -> 2)，即617 + 295
    输出：2 -> 1 -> 9，即912*/
    /*每个节点相加并保持进位*/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode cur = new ListNode(0);
        ListNode ans = cur;
        int curry = 0;//进位
        while (l1 != null && l2 != null) {
            int num = l1.val + l2.val + curry;
            curry = num / 10;
            ListNode node = new ListNode(num % 10);
            cur.next = node;
            l1 = l1.next;
            l2 = l2.next;
            cur = cur.next;
        }

        while (l1 != null) {
            int num = l1.val + curry;
            curry = num / 10;
            ListNode node = new ListNode(num % 10);
            cur.next = node;
            l1 = l1.next;
            cur = cur.next;

        }
        while (l2 != null) {
            int num = l2.val + curry;
            curry = num / 10;
            ListNode node = new ListNode(num % 10);
            cur.next = node;
            l2 = l2.next;
            cur = cur.next;

        }

        //最后进位
        if (curry > 0) {
            cur.next = new ListNode(curry);
        }
        return ans.next;
    }

    /*旋转链表：给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。*/


}
