import javax.swing.UnsupportedLookAndFeelException;

import com.wq.utils.FrameUtils;
import com.wq.view.ExportMainView;



/**
 * 程序主入口.
 * 
 * @author wuqing
 * 
 */
public class MainProc {

	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (InstantiationException e) {
			System.err.println(e.getMessage());
		} catch (IllegalAccessException e) {
			System.err.println(e.getMessage());
		} catch (UnsupportedLookAndFeelException e) {
			System.err.println(e.getMessage());
		}	
		ExportMainView app = new ExportMainView();
		FrameUtils.showView(app);
	}

}
