package gui.css;

import javafx.scene.image.Image;

public class ImageIcon {

	public Image getImgEdit() {		
		return new Image(getClass().getResourceAsStream("/gui/css/editIcon.png"));
	}

	public Image getImgDelete() {
		return new Image(getClass().getResourceAsStream("/gui/css/deleteIcon.png")); 
	}
}
