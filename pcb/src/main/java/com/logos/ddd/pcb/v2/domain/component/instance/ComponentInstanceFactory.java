package com.logos.ddd.pcb.v2.domain.component.instance;

import com.logos.ddd.pcb.v2.domain.component.type.ComponentType;

public class ComponentInstanceFactory {

    public static ComponentInstance createByType(ComponentType componentType) {
        ComponentInstance componentInstance = new ComponentInstance();
        componentInstance.setType(componentType);
        componentInstance.setPins(componentType.getPinTypes().stream().map(PinInstance::new).toList());
        return componentInstance;
    }

}
