![Logo](https://github.com/sayersauce/Traffic/blob/master/res/title.png "Logo")
<br>
A demonstration of cars at a road intersection.
<br>
Each road `A, B, C, D` have a traffic light. The time the traffic light's remain red is altered by the number of cars entering the intersection through this road.
<br>
Each car arrives randomly on a road depending on the `ingress` ratio. This car is also randomly assigned a road to leave the intersection on from the `egress` ratio.
<br>
Cars are randomly spawned depending on the `spawn rate`. With a lower spawn rate yielding more cars per second.
<br>
By default `ingress` and `egress` values are assigned for each road as these values:
<br>
Road | Ingress | Egress
--- | --- | ---
**A** | 75 | 8
**B** | 10 | 40
**C** | 10 | 30
**D** | 5 | 22
