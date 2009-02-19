varying vec4 diffuse,ambient;
varying vec3 normal,lightDir,halfVector;

void main() {	
	/*Texture stuff*/	
	gl_TexCoord[0] = gl_MultiTexCoord0;

	normal = normalize(gl_NormalMatrix * gl_Normal);
	
	lightDir = normalize(vec3(gl_LightSource[0].position));

	halfVector = normalize(gl_LightSource[0].halfVector.xyz);
	
	diffuse = gl_FrontMaterial.diffuse * gl_LightSource[0].diffuse;
	ambient = 0.3 * gl_LightSource[0].ambient;
	ambient += gl_LightModel.ambient * gl_FrontMaterial.ambient;	
		
	gl_Position = ftransform();	
} 
