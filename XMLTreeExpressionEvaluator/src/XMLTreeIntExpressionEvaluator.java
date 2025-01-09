import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Jesse Shields
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        //Initializes needed variables
        int totalVal = 0;
        int leftVal = 0;
        int rightVal = 0;
        //Recursive loop that stops when at the end of the tree, then does operations
        //on the way back up
        if (exp.numberOfChildren() > 0) {
            //evaluates the left value of the operation recursively
            leftVal = evaluate(exp.child(0));
            //evalues the right value of the operation recursively
            rightVal = evaluate(exp.child(1));
            //Checks if label is times, and if so multiplies the left and right
            //values
            if (exp.label().equals("times")) {
                totalVal = leftVal * rightVal;
                //Checks if label is divide, and if so divides left by right values
            } else if (exp.label().equals("divide")) {
                totalVal = leftVal / rightVal;
                //Checks if label is plus, and if so adds left and right value
            } else if (exp.label().equals("plus")) {
                totalVal = leftVal + rightVal;
                //Checks if label is minus, and if so subtracts right value
                //from left value
            } else if (exp.label().equals("minus")) {
                totalVal = leftVal - rightVal;
            }
            //At the end of branch, gets value from attribute for operations
        } else {
            totalVal = Integer.parseInt(exp.attributeValue("value"));
        }
        //returns integer totalVal
        return totalVal;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
