public class DoublyLinkedList {
    
    public static ListNodeDl insertAtHead (ListNodeDl head, int newValue) {
        ListNodeDl newNode = new ListNodeDl(newValue);
        newNode.next = head;
        newNode.prev = null;

        if (head != null) {
            head.prev = newNode;
        }

        return newNode;
    }

    public static ListNodeDl arrayToLL (int[] arr) {
        ListNodeDl head = null;
        for (int i = arr.length - 1; i >= 0; i--) {
            head = insertAtHead(head, arr[i]);
        }
        return head;
    }

    public static void printList (ListNodeDl head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }
    
    public static ListNodeDl copyLL (ListNodeDl head1, ListNodeDl head2) {
        if (head2 == null) {
            return null;
        }

        head1 = new ListNodeDl(-999);
        ListNodeDl head1_cpy = head1;
        while (head2 != null) {
            head1.next = new ListNodeDl(head2.val);
            head1.next.prev = head1;
            head1 = head1.next;
            head2 = head2.next;
        }
        return head1_cpy.next;
    }
}
