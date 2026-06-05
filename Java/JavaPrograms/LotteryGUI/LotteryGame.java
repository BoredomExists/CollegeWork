import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;

public class LotteryGame implements ActionListener {
    LotteryErrors error = new LotteryErrors();
    Random random = new Random();

    ImageIcon image = new ImageIcon("Lotto.png");
    JFrame inputFrame = new JFrame("Input Money");
    JFrame mainFrame = new JFrame("Lottery");

    JLabel winNumbers = new JLabel("Winning Numbers");
    JLabel moneyLabel = new JLabel("Currency");
    JLabel setMoney = new JLabel("Enter the Amount of Money");
    JLabel currentBid = new JLabel("Current Bid");

    JButton moneyButton = new JButton("Confirm Amount");
    JButton guess = new JButton("Submit Guesses");
    JButton reset = new JButton("Clear Selection");

    JTextField moneyField = new JTextField();
    JTextField bidOutput = new JTextField();
    JTextField winOutput = new JTextField();

    LinkedHashSet<Integer> lottery = new LinkedHashSet<>();
    ArrayList<JCheckBox> boxList = new ArrayList<>();
    ArrayList<JTextField> outputList = new ArrayList<>();
    ArrayList<JButton> ButtonList = new ArrayList<>();
    ArrayList<Integer> checkedBoxes = new ArrayList<>();

    Border border1 = BorderFactory.createLineBorder(Color.black, 5);
    Border border2 = BorderFactory.createLineBorder(Color.blue, 5);
    Border border3 = BorderFactory.createLineBorder(Color.green, 5);
    Font myFont = new Font("Fira Code", Font.PLAIN, 15);

    JCheckBox boxes;
    JTextField Output;
    JButton buttonBids;
    ArrayList<Integer> lottoNumbers;

    int a, i;
    int money;
    int numberLabel = 1;
    int outputX = 100;;
    int incrementRow = 50;
    int row1 = 150, row2 = 150, row3 = 150;

    public void createFirstFrame() {
        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputFrame.setSize(400, 400);
        inputFrame.setLayout(null);

        setMoney.setBounds(100, 50, 200, 50);
        setMoney.setFont(myFont);

        moneyButton.setFocusable(false);
        moneyButton.setBounds(135, 150, 125, 50);
        moneyButton.addActionListener(e -> moneyButtonPressed());

        moneyField.setBounds(150, 100, 100, 30);
        moneyField.setBorder(border3);
        moneyField.setFont(myFont);
        moneyField.setHorizontalAlignment(JTextField.CENTER);

        inputFrame.add(setMoney);
        inputFrame.add(moneyButton);
        inputFrame.add(moneyField);
        inputFrame.setVisible(true);
    }

    public void createLottoFrame() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);
        mainFrame.setIconImage(image.getImage());
        mainFrame.setSize(800, 700);

        winNumbers.setBounds(350, 25, 150, 50);

        createCurrencyOutput();
        mainFrame.add(winNumbers);
        mainFrame.setVisible(true);
    }

    public void createCheckBoxes() {
        for (a = 0; a < 30; a++) {
            boxes = new JCheckBox();
            boxList.add(boxes);
            boxList.get(a).setText(Integer.toString(numberLabel++));
            if (boxList.get(i).isSelected()) {
                System.out.println(Integer.parseInt(boxList.get(i).getText()));
            }

            if (a < 10) {
                boxList.get(a).setBounds(row1, 400, 50, 50);
                row1 = row1 + incrementRow;
            }
            if (a >= 10 && a < 20) {
                boxList.get(a).setBounds(row2, 450, 50, 50);
                row2 = row2 + incrementRow;
            }
            if (a >= 20 && a <= 30) {
                boxList.get(a).setBounds(row3, 500, 50, 50);
                row3 = row3 + incrementRow;
            }
            mainFrame.add(boxList.get(a));
        }

    }

    public void createOutputFields() {
        for (a = 0; a < 6; a++) {
            Output = new JTextField();
            outputList.add(Output);
            outputList.get(a).setBorder(border1);
            outputList.get(a).setFont(myFont);
            outputList.get(a).setEditable(false);
            outputList.get(a).setHorizontalAlignment(JTextField.CENTER);
            outputList.get(a).setBounds(outputX, 100, 100, 100);
            outputX = outputX + 100;

            mainFrame.add(outputList.get(a));
        }
    }

    public void createLottoButtons() {
        guess.setFocusable(false);
        guess.addActionListener(this);
        guess.setBounds(200, 575, 150, 50);

        reset.setFocusable(false);
        reset.addActionListener(e -> SubmitReset());
        reset.setBounds(400, 575, 150, 50);

        mainFrame.add(guess);
        mainFrame.add(reset);
    }

    public void CreateBiddingNumbers() {
        for (i = 0; i < 5; i++) {
            buttonBids = new JButton();
            ButtonList.add(buttonBids);
            ButtonList.get(i).setFocusable(false);
            ButtonList.get(i).addActionListener(this);

            mainFrame.add(ButtonList.get(i));
        }
        ButtonList.get(0).setText("5");
        ButtonList.get(0).setBounds(20, 450, 50, 20);
        ButtonList.get(1).setText("10");
        ButtonList.get(1).setBounds(70, 450, 50, 20);
        ButtonList.get(2).setText("25");
        ButtonList.get(2).setBounds(45, 475, 50, 20);
        ButtonList.get(3).setText("50");
        ButtonList.get(3).setBounds(20, 500, 50, 20);
        ButtonList.get(4).setText("100");
        ButtonList.get(4).setBounds(70, 500, 55, 20);

        currentBid.setBounds(35, 300, 75, 50);

        bidOutput.setEditable(false);
        bidOutput.setBorder(border3);
        bidOutput.setHorizontalAlignment(JTextField.CENTER);
        bidOutput.setFont(myFont);
        bidOutput.setBounds(30, 350, 75, 75);

        mainFrame.add(currentBid);
        mainFrame.add(bidOutput);
    }

    public void createWinningOutput() {
        winOutput.setHorizontalAlignment(JTextField.CENTER);
        winOutput.setFont(myFont);
        winOutput.setEditable(false);
        winOutput.setBorder(border2);
        winOutput.setBounds(200, 250, 400, 50);

        mainFrame.add(winOutput);
    }

    public void createCurrencyOutput() {
        money = Integer.parseInt(moneyField.getText());
        moneyLabel.setFont(myFont);
        moneyLabel.setBounds(350, 300, 100, 50);
        moneyField.setBounds(330, 350, 100, 50);
        moneyField.setEditable(false);
        moneyField.setText(Integer.toString(money));

        mainFrame.add(moneyLabel);
        mainFrame.add(moneyField);
    }

    public void getLottoNumbers() {
        while (lottery.size() < 6) {
            lottery.add(random.nextInt(30) + 1);
        }
        lottoNumbers = new ArrayList<>(lottery);
        for (int i = 0; i < 6; i++) {
            outputList.get(i).setText(Integer.toString(lottoNumbers.get(i)));
        }
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        try {
            int matches = 0;
            for (i = 0; i < 5; i++) {
                if (e.getSource() == ButtonList.get(i)) {
                    bidOutput.setText(ButtonList.get(i).getText());
                }
            }

            if (e.getSource() == guess) {
                int bid = Integer.parseInt(bidOutput.getText());
                lottery.clear();
                checkedBoxes.clear();
                getLottoNumbers();

                for (i = 0; i < 30; i++) {
                    if (boxList.get(i).isSelected()) {
                        checkedBoxes.add(Integer.parseInt(boxList.get(i).getText()));
                    }
                }

                if (checkedBoxes.size() <= 5) {
                    error.createLessError();
                } else if (checkedBoxes.size() >= 7) {
                    error.createGreatError();
                } else if (checkedBoxes.size() == 6) {
                    for (i = 0; i < 6; i++) {
                        if (checkedBoxes.get(i) == lottoNumbers.get(i)) {
                            matches++;
                        }
                    }

                    if (matches == 0) {
                        money = money - bid;
                    } else if (matches == 1) {
                        money = money + (bid * 1);
                    } else if (matches == 2) {
                        money = money + (bid * 2);
                    } else if (matches == 3) {
                        money = money + (bid * 3);
                    } else if (matches == 4) {
                        money = money + (bid * 4);
                    } else if (matches == 5) {
                        money = money + (bid * 5);
                    } else if (matches == 6) {
                        money = money + (bid * 6);
                    }
                    if (money <= 0) {
                        mainFrame.setVisible(false);
                        error.createBrokeFrame();
                    }
                    moneyField.setText(Integer.toString(money));

                    createWinningOutput();
                    winOutput.setText("Congrats, You have " + matches + " matches.");
                }
            }
        } catch (Exception exc) {
            error.createNoInputError();
            System.out.println(exc.getClass());
            System.out.println(exc.getMessage());
        }
    }

    void SubmitReset() {
        for (i = 0; i < 30; i++) {
            boxList.get(i).setSelected(false);
        }
    }

    void moneyButtonPressed() {
        try {
            createLottoFrame();
            inputFrame.setVisible(false);
        } catch (Exception excep) {
            error.createFirstFrameError();

        }
    }

}
