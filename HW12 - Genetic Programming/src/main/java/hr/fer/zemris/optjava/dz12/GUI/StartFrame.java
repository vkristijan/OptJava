package hr.fer.zemris.optjava.dz12.GUI;

import hr.fer.zemris.optjava.dz12.node.terminal.TerminalNode;

import javax.swing.*;
import java.awt.*;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class StartFrame extends JFrame {
    private static final long serialVersionUID = 1;

    private JAntMap antMap;
    private int index = 0;
    private int time = 60;
    private Timer timer;

    public StartFrame(JAntMap antMap) throws HeadlessException {
        this.antMap = antMap;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AntTrailGA");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        initGUI();
        setVisible(true);
    }

    private void initGUI() {
        Container cp = getContentPane();

        cp.setLayout(new BorderLayout());
        cp.add(antMap, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton btnNext = new JButton("Next");
        JButton btnStart = new JButton("Start");

        panel.setLayout(new GridLayout(0, 2));
        panel.add(btnNext);
        panel.add(btnStart);

        timer = new Timer(time, (t) -> next());

        btnNext.addActionListener((l) -> next());
        btnStart.addActionListener((l) -> {
            if (!timer.isRunning()) {
                timer.start();
            }
        });

        cp.add(panel, BorderLayout.SOUTH);

    }

    private void next(){
        if (index >= antMap.getNodeCount()){
            if (timer.isRunning()){
                timer.stop();
            }
            return;
        }

        TerminalNode node = antMap.getNode(index);
        node.action(antMap.getMap(), 0);

        index++;

        antMap.validate();
        antMap.repaint();
    }
}
