class Vector {
	public float x;
	public float y;
	public float z;
	public Vector normal;
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(float x, float y, float z, Vector normal) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.normal = normal;
	}
}
