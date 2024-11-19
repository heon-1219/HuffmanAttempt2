
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HuffmanTree<E extends Comparable<? super E>> {
    private TreeNode root;
    private Map<Integer, String> huffManCodes;
    private Map<Integer, Integer> getFreqPerCode;
    private int sum;

    // TODO Check with TAs if this has to be generic or not
    // Is this still needed to be asked? @mehtavihaanj
    public HuffmanTree(PriorityQueue314<TreeNode> pq) {
        this();
        ArrayList<Integer> list = new ArrayList<>();
        Iterator<TreeNode> it = pq.iterator();

        while (it.hasNext()) {
            TreeNode temp = it.next();
            list.add(temp.getValue());
            getFreqPerCode.put(temp.getValue(), temp.getFrequency());
        }

        while (pq.size() > 1) {
            TreeNode left = pq.poll();
            TreeNode right = pq.poll();
            pq.enqueue(new TreeNode(left, -1, right));
        }
        root = pq.poll();

        // System.out.println("DATA: " + root.getRight().getLeft().getValue());
        // Collections.sort(list);
        // System.out.println(list);
        for (Integer value : list) {
            huffManCodes.put(value, getCode(value));
        }

        for (Integer value : huffManCodes.keySet()) {
            System.out.println("Code: " + huffManCodes.get(value) + " Frequency: " + getFreqPerCode.get(value));
            sum += huffManCodes.get(value).length() * getFreqPerCode.get(value);
        }
    }

    private String getCode(int value) {
        StringBuilder sb = new StringBuilder();
        TreeNode node = root;
        getCodeHelper(value, node, sb);
        return sb.toString();
    }

    public int getSumOfAllCodes() {
        return sum;
    }

    private int getCodeHelper(int value, TreeNode node, StringBuilder code) {
        if (node != null && node.getValue() != value) {
            code.append(0);
            int leftResult = getCodeHelper(value, node.getLeft(), code);
            if (leftResult == value) {
                return leftResult;
            }
            code.replace(code.length() - 1, code.length(), "");

            code.append(1);
            int rightResult = getCodeHelper(value, node.getRight(), code);
            if (rightResult == value) {
                return rightResult;
            }
            code.replace(code.length() - 1, code.length(), "");
        } else if (node != null && node.getValue() == value) {
            return value;
        }
        return -1;
    }

    public HuffmanTree() {
        huffManCodes = new HashMap<>();
        getFreqPerCode = new HashMap<>();
    }

    public HashMap<Integer, String> getHuffManCodes() {
        return (HashMap<Integer, String>) huffManCodes;
    }

    // for STF
    public String treeHeader() {
        StringBuilder header = new StringBuilder();

        for (Map.Entry<Integer, String> entry : huffManCodes.entrySet()) {
            String huffCode = entry.getValue();
            header.append(huffCode);

            int character = entry.getKey();
            StringBuilder binary = new StringBuilder();
            while (character > 0) {
                int remainder = character % 2;
                binary.insert(0, remainder);
                character = character / 2;
            }
            header.append(binary);
        }

        return header.toString();
    }
}