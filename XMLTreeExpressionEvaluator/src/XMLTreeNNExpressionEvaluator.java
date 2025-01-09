import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Jesse Shields
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires * [exp is a subtree of a well-formed XML arithmetic expression]
     *           and [the label of the root of exp is not "expression"]
     *
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        NaturalNumber totalVal = new NaturalNumber2();
        NaturalNumber leftVal = new NaturalNumber2();
        NaturalNumber rightVal = new NaturalNumber2();
        //Recursive loop that stops when at the end of a branch, then does operations
        //on the way back up
        if (exp.numberOfChildren() > 0) {
            //evaluates the left value of the operation recursively
            leftVal = evaluate(exp.child(0));
            //evalues the right value of the operation recursively
            rightVal = evaluate(exp.child(1));
            //Checks if label is times, and if so copies left value and
            //multiplies by right value
            if (exp.label().equals("times")) {
                totalVal.copyFrom(leftVal);
                totalVal.multiply(rightVal);
                //Checks if label is divide, and if so copies left value and
                //divides by right value
            } else if (exp.label().equals("divide")) {
                //Checks if the right value is 0 and reports an error if so
                if (rightVal.isZero()) {
                    Reporter.fatalErrorToConsole("PreCon violation: Cannot divide by 0");
                }
                totalVal.copyFrom(leftVal);
                totalVal.divide(rightVal);
                //Checks if label is plus and if so copies left value and
                //adds right value
            } else if (exp.label().equals("plus")) {
                totalVal.copyFrom(leftVal);
                totalVal.add(rightVal);
                //Checks if label is minus and if so copies left value and
                //subtracts left value
            } else if (exp.label().equals("minus")) {
                //Checks if right value is larger than left, and if so throws
                //an error since NNs cannot be negative
                if (leftVal.compareTo(rightVal) < 0) {
                    Reporter.fatalErrorToConsole(
                            "PreCon violation: NN cannot be negative after minus");

                } else {
                    totalVal.copyFrom(leftVal);
                    totalVal.subtract(rightVal);
                }
            }
            //Gets value from attribute at the end of branch for operations
        } else {
            totalVal = new NaturalNumber2(exp.attributeValue("value"));
        }
        //Returns NaturalNumber totalVal
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
