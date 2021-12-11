package model;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

/**
 * @author Maxpicca
 * @version 创建时间：2020-12-14
 * @Description 音效库
 */

public class MusicBase {
	public AudioClip soundMove;
	public AudioClip soundDirect;
	public AudioClip soundHit;
	public AudioClip soundScore;
	public AudioClip soundBgm;
	public AudioClip soundOver;
	public AudioClip soundPause;
	public AudioClip soundStart;
	public AudioClip soundBtn;

	public MusicBase() {
		try {
			URL soundMoveUrl = new File("material/soundMove.wav").toURI().toURL();
			soundMove = Applet.newAudioClip(soundMoveUrl);

			URL soundDirectUrl = new File("material/soundDirect.wav").toURI().toURL();
			soundDirect = Applet.newAudioClip(soundDirectUrl);

			URL soundHitUrl = new File("material/soundHit.wav").toURI().toURL();
			soundHit = Applet.newAudioClip(soundHitUrl);

			URL soundScoreUrl = new File("material/soundScore.wav").toURI().toURL();
			soundScore = Applet.newAudioClip(soundScoreUrl);

			URL soundBgmUrl = new File("material/soundBgm.wav").toURI().toURL();
			soundBgm = Applet.newAudioClip(soundBgmUrl);

			URL soundOverUrl = new File("material/soundOver.wav").toURI().toURL();
			soundOver = Applet.newAudioClip(soundOverUrl);

			URL soundPauseUrl = new File("material/soundPause.wav").toURI().toURL();
			soundPause = Applet.newAudioClip(soundPauseUrl);

			URL soundStartUrl = new File("material/soundStart.wav").toURI().toURL();
			soundStart = Applet.newAudioClip(soundStartUrl);

			URL soundBtnUrl = new File("material/soundBtn.wav").toURI().toURL();
			soundBtn = Applet.newAudioClip(soundBtnUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "游戏异常退出");
			System.exit(0);
		}
	}

	/*
	 * public static void main(String[] args) { Music music = new Music();
	 * music.soundStart.loop(); try { Thread.sleep(6000); } catch
	 * (InterruptedException e) {
	 * e.printStackTrace(); } }
	 */

}
