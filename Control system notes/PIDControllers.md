A **PID controller**, which stands for **proportional integral derivative controller**, is a commonly used type of feedback controller that continuously measures the gap between the controller input signal and the output of the plant and corrects the margin of error.
For a PID controller, the refrence is instead called the **setpoint**, and it's still denoted by **𝑟(𝒕)**. 


The 3 component terms of PID, those being the proportional, integral and derivative terms, each have the following unique roles:

1. **Proportional:** The proportional term drives the position error to zero. A proportional controller's input is defined by the equation: 

**𝒖(𝒕) = 𝐾<sub>𝑝</sub> * 𝒆(𝒕)**, where **𝐾<sub>𝑝</sub>** is the proportional gain and **𝒆(𝒕)** is the error at time **𝒕**.

In other words, the controller input equals the gain times the error. A proportional controller acts like a software-defined spring that pulls the output closer and closer to the setpoint.

2. **Derivative:** The derivative term drives the velocity error to zero. A proportional derivative (PD) controller's input is defined by the equation:

**𝒖(𝒕) = 𝐾<sub>𝑝</sub> * 𝒆(𝒕) + 𝐾<sub>𝑑</sub> 𝑑𝑒/𝑑𝑡**, where **𝐾<sub>𝑝</sub>** is the proportional gain, **𝐾<sub>𝑑</sub>** is the derivative gain, and **𝒆(𝒕)** is the error at time **𝒕**.

The purpose of the derivative is to drive the velocity of the setpoint, i.e. the rate at which the setpoint changes, closer to the velocity of the plant.

3. **Integral:** The integral term takes the area between the curves of the functions **𝑟(𝒕)** and **𝒚(𝒕)**, then drives that area to zero. The equation for controller input over time for a proportional integral (PI) controller is the following: 

**𝒖(𝒕) = 𝐾<sub>𝑝</sub> * 𝒆(𝒕) + 𝐾<sub>𝑖</sub>∫₀<sup>𝒕</sup>𝒆(𝛕)𝑑𝛕**, where **𝐾<sub>𝑝</sub>** is the proportional gain, **𝐾<sub>𝑖</sub>** is the integral gain, **𝒆(𝒕)** is the error at time **𝒕**, and **𝛕** is the integration variable.

This gives us the final equation for a proportional integral derivative (PID) controller to be: 

**𝒖(𝒕) = 𝐾<sub>𝑝</sub> * 𝒆(𝒕) + 𝐾<sub>𝑖</sub>∫₀<sup>𝒕</sup>𝒆(𝛕)𝑑𝛕 + 𝐾<sub>𝑑</sub> 𝑑𝑒/𝑑𝑡**, where **𝐾<sub>𝑝</sub>** is the proportional gain, **𝐾<sub>𝑖</sub>** is the integral gain, **𝐾<sub>𝑑</sub>** is the derivative gain, **𝒆(𝒕)** is the error at the current time **𝒕**, and **𝛕** is the integration variable.

PID controllers only work in systems where there is only one input and one output. Also, they best used when the dynamics of the plant are unknown and there is no dynamic model of the plant's motion. When there is a model, better controllers can be used.