using UnityEngine;
public class ApexCar : MonoBehaviour {
 public float acceleration=22f, steering=85f, drag=0.98f;
 public float velocity; public float steerInput, throttleInput;
 void Update() {
  float throttle=throttleInput + Input.GetAxisRaw("Vertical");
  float steer=steerInput + Input.GetAxisRaw("Horizontal");
  velocity=Mathf.Clamp(velocity+throttle*acceleration*Time.deltaTime,-12f,65f);
  velocity*=Mathf.Pow(drag,Time.deltaTime*60f);
  transform.Rotate(0,steer*steering*Time.deltaTime*Mathf.Clamp01(Mathf.Abs(velocity)/8f)*Mathf.Sign(velocity),0);
  transform.position+=transform.forward*velocity*Time.deltaTime;
 }
}