package fonts;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class FontManager {
    private static void setUIFont(FontUIResource fontUIResource) {
        UIManager.put("Button.font", fontUIResource);
        UIManager.put("ToggleButton.font", fontUIResource);
        UIManager.put("RadioButton.font", fontUIResource);
        UIManager.put("CheckBox.font", fontUIResource);
        UIManager.put("ColorChooser.font", fontUIResource);
        UIManager.put("ComboBox.font", fontUIResource);
        UIManager.put("Label.font", fontUIResource);
        UIManager.put("List.font", fontUIResource);
        UIManager.put("MenuBar.font", fontUIResource);
        UIManager.put("MenuItem.font", fontUIResource);
        UIManager.put("RadioButtonMenuItem.font", fontUIResource);
        UIManager.put("CheckBoxMenuItem.font", fontUIResource);
        UIManager.put("Menu.font", fontUIResource);
        UIManager.put("PopupMenu.font", fontUIResource);
        UIManager.put("OptionPane.font", fontUIResource);
        UIManager.put("Panel.font", fontUIResource);
        UIManager.put("ProgressBar.font", fontUIResource);
        UIManager.put("ScrollPane.font", fontUIResource);
        UIManager.put("Viewport.font", fontUIResource);
        UIManager.put("TabbedPane.font", fontUIResource);
        UIManager.put("Table.font", fontUIResource);
        UIManager.put("TableHeader.font", fontUIResource);
        UIManager.put("TextField.font", fontUIResource);
        UIManager.put("PasswordField.font", fontUIResource);
        UIManager.put("TextArea.font", fontUIResource);
        UIManager.put("TextPane.font", fontUIResource);
        UIManager.put("EditorPane.font", fontUIResource);
        UIManager.put("TitledBorder.font", fontUIResource);
        UIManager.put("ToolBar.font", fontUIResource);
        UIManager.put("ToolTip.font", fontUIResource);
        UIManager.put("Tree.font", fontUIResource);
    }
}