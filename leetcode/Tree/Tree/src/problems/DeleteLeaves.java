package problems;

import node.TreeNode;

public class DeleteLeaves {
    public TreeNode removeLeafNodes(TreeNode root, int target) {

        if (root == null)
            return root;

        removeLeafNodes(root.left, target);
        removeLeafNodes(root.right, target);

        if (root.left != null && root.left.val == target && root.left.left == null && root.left.right == null)
            root.left = null;
        if (root.right != null && root.right.val == target && root.right.left == null && root.right.right == null)
            root.right = null;
//        else
//            System.out.println(root.val);
        if (root.left == null && root.right == null && root.val == target)
            root = null;

        return root;
    }
}
