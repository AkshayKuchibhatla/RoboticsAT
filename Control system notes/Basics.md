# Control System Basics

**GAIN:** Each control system has an input from the user and an output sent to the device. There is an input signal and an output signal. **Gain** is the ratio of the amplitude of the output signal to the amplitude of the input signal. For example, if the output signal has twice the amplitude of the input signal, then the control system has a gain of 2.

**PLANT:** The **plant** is the system being controlled by the controller.

**OPEN-LOOP CONTROLLER:** Also known as a **feed-forward controller**, an open-loop control system does not involve any information from the plant's output when sending signals. Such a system does not check to see whether the plant has reached the desired state that the controller wants it to reach. Rather, it predicts what the plant will do for a certain signal and sends that signal. 

**CLOSED-LOOP CONTROLLER:** Also known as a **feedback controller**, a closed-loop control system, while sending signals, constantly checks to see whether the plant's output is matching the intended output. Then, it accounts for the margin of error by sending correction signals.

A feedforward controller can be useful for injecting important signals into the plant, while a feedback controller is best used when there are many external forces being placed on the plant that can create a margin of error from the signal sent to the plant.

**REFERENCE:** The desired state that the controller must drive the plant towards. This is used in the controller's calculation of margin of error. It is often described as a funciton of time, **𝑟(𝒕)**.

**CONTROL INPUT:** The input sent to a plant to control it. This is described as a function of time, **𝒖(𝒕)**.

**OUTPUT:** Measurements of the plant's state from sensors. This is described as a function of time, **𝒚(𝒕)**.

**ERROR:** The refrence minus the output or the state of the plant. This is described as a function of time, **𝒆(𝒕)**.