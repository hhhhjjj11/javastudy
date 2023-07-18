package com.ssafy.board.model;

public class FileInfoDto {

	// db에서 정한 필드 중에서 두 개가 빠졋음 : idx 와  article_no
	// 먼저 idx의 경우에는 db에서 자동으로 만들어주고,
	// article_no는 컨트롤러나 매퍼에서 따로 삽입해주는 듯.
	private String saveFolder;
	private String originalFile;
	private String saveFile;

	public String getSaveFolder() {
		return saveFolder;
	}

	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}

	public String getOriginalFile() {
		return originalFile;
	}

	public void setOriginalFile(String originalFile) {
		this.originalFile = originalFile;
	}

	public String getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}

}
