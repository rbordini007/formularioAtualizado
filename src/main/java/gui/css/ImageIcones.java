package gui.css;

import javafx.scene.image.Image;

public class ImageIcones {	

	public Image getImgEdit() {				
		return new Image(getClass().getResourceAsStream("/gui/css/editIcon.png"));
	}

	public Image getImgDelete() {
		return new Image(getClass().getResourceAsStream("/gui/css/deleteIcon.png")); 
	}
	
	public Image getImgRecarregar() {
		return new Image(getClass().getResourceAsStream("/gui/css/reload_29430.png"));
	}
}
