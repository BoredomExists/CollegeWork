
import javax.swing.*;
import java.awt.Color;

public class LotteryErrors {
    JFrame inGreaterError = new JFrame("🛑Greater Input Error");
    JFrame inLesserError = new JFrame("🛑Lesser Input Error");
    JFrame inMoneyError = new JFrame("🛑Money Input Error");
    JFrame brokeFrame = new JFrame("No More Money");
    JFrame firstFrameError = new JFrame("No Money Input Error");
    JFrame bidBoxSelectionError = new JFrame("Error in Box/Bid Input");

    JLabel inputMoneyError = new JLabel("⛔You need to input an amount⛔");
    JLabel inputGreaterError = new JLabel("⛔Cannot select more than 6 boxes⛔");
    JLabel inputLesserError = new JLabel("⛔Cannot select less than 6 boxes⛔");
    JLabel brokeMoney = new JLabel("💲You have no more money to bet💲");
    JLabel firstFrameLabel = new JLabel("⛔No Money has been Inputed⛔");
    JLabel bidBoxSelectionLabel = new JLabel(
            "⛔Either no bid amount has been selected or no guesses have been selected⛔");

    public void createBrokeFrame() {
        brokeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        brokeFrame.setSize(400, 400);
        brokeFrame.setLayout(null);

        brokeMoney.setForeground(Color.green);
        brokeMoney.setBounds(100, 100, 250, 50);

        brokeFrame.add(brokeMoney);
        brokeFrame.setVisible(true);
    }

    public void createMoneyError() {
        inMoneyError.setSize(400, 400);
        inMoneyError.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        inMoneyError.setLayout(null);

        inputMoneyError.setBounds(100, 100, 250, 50);
        inputMoneyError.setForeground(Color.red);

        inMoneyError.add(inputMoneyError);
        inMoneyError.setVisible(true);
    }

    public void createGreatError() {
        inGreaterError.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        inGreaterError.setLayout(null);
        inGreaterError.setSize(400, 400);

        inputGreaterError.setBounds(100, 100, 250, 50);
        inputGreaterError.setForeground(Color.red);

        inGreaterError.add(inputGreaterError);
        inGreaterError.setVisible(true);
    }

    public void createLessError() {
        inLesserError.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        inLesserError.setLayout(null);
        inLesserError.setSize(400, 400);

        inputLesserError.setBounds(100, 100, 250, 50);
        inputLesserError.setForeground(Color.red);

        inLesserError.add(inputLesserError);
        inLesserError.setVisible(true);
    }

    public void createFirstFrameError() {
        firstFrameError.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        firstFrameError.setLayout(null);
        firstFrameError.setSize(400, 400);

        firstFrameLabel.setBounds(100, 100, 250, 50);
        firstFrameLabel.setForeground(Color.red);

        firstFrameError.add(firstFrameLabel);
        firstFrameError.setVisible(true);
    }

    public void createNoInputError() {
        bidBoxSelectionError.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        bidBoxSelectionError.setLayout(null);
        bidBoxSelectionError.setSize(600, 400);

        bidBoxSelectionLabel.setBounds(75, 100, 500, 50);
        bidBoxSelectionLabel.setForeground(Color.red);

        bidBoxSelectionError.add(bidBoxSelectionLabel);
        bidBoxSelectionError.setVisible(true);
    }
}
