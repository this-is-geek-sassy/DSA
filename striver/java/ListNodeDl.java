public class ListNodeDl {
    public int val;
    public ListNodeDl prev;
    public ListNodeDl next;

    public ListNodeDl() {}
    public ListNodeDl(int val) {
        this.val = val;
    }
    public ListNodeDl (int val, ListNodeDl prev, ListNodeDl next) {
        this.val = val;
        this.prev = prev;
        this.next = next;
    }
}
