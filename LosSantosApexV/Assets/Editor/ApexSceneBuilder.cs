using UnityEngine;
using UnityEditor;
using UnityEditor.SceneManagement;
public static class ApexSceneBuilder {
 [MenuItem("Los Santos Apex V/Generate Playable City")]
 public static void Generate() {
  var scene=EditorSceneManager.NewScene(NewSceneSetup.DefaultGameObjects,NewSceneMode.Single);
  var ground=GameObject.CreatePrimitive(PrimitiveType.Cube);
  ground.name="City Ground";ground.transform.localScale=new Vector3(400,1,400);
  ground.transform.position=new Vector3(0,-0.6f,0);
  var car=new GameObject("Apex Supercar");
  car.transform.position=new Vector3(0,1,0);
  car.AddComponent<ApexCar>();
  var body=GameObject.CreatePrimitive(PrimitiveType.Cube);
  body.name="Car Body";body.transform.SetParent(car.transform,false);
  body.transform.localScale=new Vector3(2,0.7f,4);
  body.GetComponent<Renderer>().sharedMaterial=new Material(Shader.Find("Standard")){color=new Color(.35f,.05f,.8f)};
  for(int i=0;i<4;i++){
   var wheel=GameObject.CreatePrimitive(PrimitiveType.Cylinder);
   wheel.name="Wheel";wheel.transform.SetParent(car.transform,false);
   wheel.transform.localScale=new Vector3(.45f,.18f,.45f);
   wheel.transform.localRotation=Quaternion.Euler(0,0,90);
   wheel.transform.localPosition=new Vector3(i%2==0?-1.1f:1.1f,-.4f,i<2?-1.25f:1.25f);
  }
  var cam=Camera.main;
  if(cam==null){var obj=new GameObject("Main Camera");cam=obj.AddComponent<Camera>();obj.tag="MainCamera";}
  cam.gameObject.AddComponent<ApexCamera>().target=car.transform;
  for(int x=-4;x<=4;x++)for(int z=-4;z<=4;z++){
   if(x==0||z==0)continue;
   var b=GameObject.CreatePrimitive(PrimitiveType.Cube);
   b.name="Building";b.transform.position=new Vector3(x*25,8+((x*x+z*z)%5)*5,z*25);
   b.transform.localScale=new Vector3(14,b.transform.position.y*2,14);
  }
  System.IO.Directory.CreateDirectory("Assets/Scenes");
  EditorSceneManager.SaveScene(scene,"Assets/Scenes/ApexCity.unity");
  EditorBuildSettings.scenes=new[]{new EditorBuildSettingsScene("Assets/Scenes/ApexCity.unity",true)};
 }
}