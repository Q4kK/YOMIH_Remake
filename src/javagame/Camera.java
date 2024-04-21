package javagame;

public class Camera {

	int x = 0;
	int y = 0;
	int z = 0;

	public Camera(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	// X: total distance of camera
	// Y: distance between two center points
	// Z: distance between center points and camera wall
	// cen1/cen2: center points for players
	public int[] calculateCamera(int X, int cen1, int cen2) {
		// TODO: might not need y
		int Y = Math.abs(cen1 - cen2);
		int Z = Math.abs(X - Y);
		X = Y + Z;

		return new int[] { X, Y, Z };

	}

}
