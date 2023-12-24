import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BusWindow extends JFrame {
    private List<Bus> busList;  // 私有成员变量保存公交车列表

    private JLabel topPanelLabel;
    private JLabel bottomPanelLabel;
    private JTextField demandTextField;
    private JButton matchButton;

    public BusWindow(List<Bus> busList) {
        Collections.sort(busList, new Comparator<Bus>() {
            @Override
            public int compare(Bus o1, Bus o2) {
                return Integer.compare(o1.getNum(), o2.getNum());
            }
        });
        this.busList = busList;

        // 设置窗口属性
        setTitle("公交车");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 初始化顶部面板
        topPanelLabel = new JLabel("车牌：无，载客量：无");
        topPanelLabel.setHorizontalAlignment(JLabel.CENTER);
        add(topPanelLabel, BorderLayout.NORTH);

        // 初始化底部面板
        JPanel bottomPanel = new JPanel();
        bottomPanelLabel = new JLabel("需求载客人数：");
        demandTextField = new JTextField("", 10);
        matchButton = new JButton("匹配");

        // 为匹配按钮添加点击事件监听器
        matchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                matchBus();
            }
        });

        bottomPanel.add(bottomPanelLabel);
        bottomPanel.add(demandTextField);
        bottomPanel.add(matchButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 匹配公交车
    private void matchBus() {
        try {
            int demand = Integer.parseInt(demandTextField.getText());
            Bus matchedBus = findMatchingBus(demand);
            if (matchedBus != null) {
                topPanelLabel.setText("车牌：" + matchedBus.getLicensePlate() + "，载客量：" + matchedBus.getNum());
            } else {
                topPanelLabel.setText("车牌：无，载客量：无");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的需求载客人数！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 在公交车列表中查找最匹配的车辆
    private Bus findMatchingBus(int demand) {
        Bus bestMatch = null;

        for (Bus bus : busList) {
            int difference = bus.getNum() - demand;
            if (difference > 0) {
                bestMatch = bus;
                break;
            }
        }

        return bestMatch;
    }

    public static void main(String[] args) {
        // 假设有一个公交车列表
        List<Bus> busList = Arrays.asList(
                new Bus("Bus001", "a", 10, 60),
                new Bus("Bus002", "a", 10, 70),
                new Bus("Bus003", "a", 10, 80)
        );

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BusWindow busWindow = new BusWindow(busList);
                busWindow.setVisible(true);
            }
        });
    }
}
