package com.example.enigma.entry;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//エニグマの設定情報を保持するクラス

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SettingEnigma")
public class SettingEnigma {
	
	
	@Id
	@Column(value = "id")
	private Integer id;
	
	//各ルーターの設定
	@Column(value = "router")
	private Integer[] router = {1, 2, 3};
	
	//各ルーターの開始位置
	@Column(value = "routerStartPoint")
	private Integer[] routerStatrPoint = {0, 0, 0};
	
	//反転ルーターの設定
	@Column(value = "reverseRouter")
	private Integer reverseRouter = 1;
	
	//プラグボードの設定
	//プラグボードAの設定
	//プラグボードAのi番目はプラグボードBのplugBoardA[i]番目とつながっている
	@Column(value = "plugBoardA")
	private Integer[] plugBoardA = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
	
	//プラグボードBの設定
	//プラグボードBのi番目はプラグボードAのplugBoardB[i]番目とつながっている
	@Column(value = "plugBoardB")
	private Integer[] plugBoardB = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
		
	
	
	
	
}
