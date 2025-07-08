package com.diffchecker;

import java.awt.*;
import javax.swing.*;

// IMPORT COMPONENTS
import com.diffchecker.components.LineNumberingTextArea;
import com.diffchecker.components.MenuBuilder;
import com.diffchecker.components.SplitTextTabPanel;

public class Main extends JFrame {

    // ─── Static Resources ──────────────────────────────────────────────────────
    private static final String PACKAGE_NAME = "diffchecker";
    private static final ImageIcon LOGO = new ImageIcon(
            Main.class.getResource("/" + PACKAGE_NAME + "/images/logo/logo.png"));

    // ─── Instance UI Parts ─────────────────────────────────────────────────────
    private final JPanel container = new JPanel();

    // Scrollable Split Text Areas
    private static final JTextArea jt1 = new JTextArea();
    private static final JTextArea jt2 = new JTextArea();

    private static final JScrollPane scroll1 = new JScrollPane(jt1);
    private static final JScrollPane scroll2 = new JScrollPane(jt2);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public Main() {
        // ── 1. Frame ----------------------------------------------------------------
        setTitle("Diffchecker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(LOGO.getImage());
        setUndecorated(true);
        getContentPane().setBackground(new Color(0x1F1F1F));
        setSize(1080, 720);

        // Initial rounded shape
        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

        // Listen for resizes to reapply rounded shape
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            }
        });

        // ── 2. Root Wrapper ---------------------------------------------------------
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BorderLayout());
        wrapper.setBackground(new Color(36, 37, 38));
        wrapper.setOpaque(true);

        // ── 3. Custom Title Bar -----------------------------------------------------
        CustomTitleBar titleBar = new CustomTitleBar(
                this,
                "Diffchecker",
                PACKAGE_NAME,
                "/" + PACKAGE_NAME + "/images/logo/logo_24x24.png",
                new Color(36, 37, 38),
                40);

        // FOR DEBUGGING PURPOSES ONLY
        // titleBar.setBorder(BorderFactory.createLineBorder(Color.RED));

        // small transparent insets so the resize “hot zone” is reachable
        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setOpaque(false);
        titleWrapper.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        titleWrapper.add(titleBar, BorderLayout.CENTER);

        // ── 4. Menu Bar -------------------------------------------------------------
        JMenuBar menuBar = MenuBuilder.buildMenuBar();

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(new Color(36, 37, 38));
        menuPanel.add(menuBar, BorderLayout.CENTER);
        menuPanel.setPreferredSize(new Dimension(0, 30));
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        menuPanel.setMinimumSize(new Dimension(0, 30));

        // FOR DEBUGGING PURPOSES ONLY
        // menuPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        // ── 5. Main Content Panel ---------------------------------------------------
        container.setBackground(new Color(0x1F1F1F));
        container.setLayout(new BorderLayout());

        // Add line numbers to both scroll panes
        scroll1.setRowHeaderView(new LineNumberingTextArea(jt1));
        scroll2.setRowHeaderView(new LineNumberingTextArea(jt2));

        JPanel p1 = new JPanel(new BorderLayout());
        p1.add(scroll1, BorderLayout.CENTER);

        JPanel p2 = new JPanel(new BorderLayout());
        p2.add(scroll2, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p1, p2);
        splitPane.setDividerLocation(540);

        JButton btn = new JButton("Copy Text");
        btn.setSize(100, 30);
        btn.addActionListener(e -> jt2.setText(jt1.getText()));

        // TABS FUNCTIONALITY
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tab 1", new SplitTextTabPanel());
        // You can add more tabs like this if needed
        tabbedPane.addTab("Tab 2", new SplitTextTabPanel());

        container.add(tabbedPane, BorderLayout.CENTER);

        // FOR DEBUGGING PURPOSES ONLY
        // container.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        // ── 6. Compose --------------------------------------------------------------
        setContentPane(wrapper);

        // vertical box to hold both menu and container
        JPanel centerContent = new JPanel();

        centerContent.setLayout(new BoxLayout(centerContent, BoxLayout.Y_AXIS));
        centerContent.setBackground(new Color(36, 37, 38));
        centerContent.add(menuPanel);
        centerContent.add(container);

        // compose final layout
        wrapper.add(titleWrapper, BorderLayout.NORTH);
        wrapper.add(centerContent, BorderLayout.CENTER);

        // ── 7. Center on screen -----------------------------------------------------
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);

        // ── 8. Enable edge resizing -------------------------------------------------
        ComponentResizer resizer = new ComponentResizer();
        resizer.registerComponent(this);

        setVisible(true);

    }

}