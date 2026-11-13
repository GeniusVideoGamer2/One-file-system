using UnityEngine;
public class ApexCamera : MonoBehaviour {
 public Transform target;
 void LateUpdate() {
  if(!target)return;
  Vector3 p=target.position-target.forward*9f+Vector3.up*4f;
  transform.position=Vector3.Lerp(transform.position,p,Time.deltaTime*5f);
  transform.LookAt(target.position+Vector3.up*1.2f);
 }
}