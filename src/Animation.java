/** Determines which scene should be drawn for each animation
 * 	Adventure Time
 * ICS 3UP 2013,-104
 * @Gabe House
 * @version 2014/06/08
 */ 

import java.awt.Image;
import java.util.ArrayList;

public class Animation {
	public ArrayList scenes;
	private int sceneIndex;
	private long animationTime;
	private long totalTime;

	public Animation() {
		scenes = new ArrayList();
		totalTime = 0;
		start();
	}

	public synchronized void addScene(Image i, long t) {
		totalTime += t;
		scenes.add(new OneScene(i, totalTime));
	}

	public int getSceneIndex() {
		return sceneIndex;
	}

	// start animation from beginning
	public synchronized void start() {
		//refresh animation
		animationTime = 0;
		sceneIndex = 0;
	}


	public synchronized void update(long timePassed, boolean repeating) {
		// change scenes
		if (scenes.size() > 1) {

			if (timePassed > 40) {
				timePassed = 0;
			}

			if (repeating) {
				animationTime += timePassed;

				if (animationTime >= totalTime) {

					animationTime = 0;
					sceneIndex = 0;
				}
				if (animationTime > getScene(sceneIndex).endTime) {

					sceneIndex++;
				}
			} else {

				animationTime += timePassed;

				if (animationTime > getScene(sceneIndex).endTime
						&& sceneIndex < scenes.size() - 1) {
					sceneIndex++;
					System.out.println(sceneIndex);
					System.out.println(scenes.size());
				}
				if (animationTime >= totalTime) {

					animationTime = 0;
				}

			}
		}
	}


	public synchronized Image getImage() {
		// get animations current scene
		if (scenes.size() == 0) {
			return null;
		} else {
			return getScene(sceneIndex).pic;
		}
	}


	private OneScene getScene(int x) {
		// get scene
		return (OneScene) scenes.get(x);
	}

	// Private inner class////

	private class OneScene {
		Image pic;
		long endTime;

		public OneScene(Image pic, long endTime) {
			this.pic = pic;
			this.endTime = endTime;
		}
	}
}
