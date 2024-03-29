# to-do list
* [x] remove PinType of ComponentType
* [x] remove PinType
* [x] refactor link
* [x] move repository to application
* [x] get outPutPinNumber from ComponentType instead of PinInstance
* [x] remove PinInstance
* [x] add Pin
* [x] refactor LinkChipService
* [x] rewrite link logic in use case
* [x] remove ComponentInstanceFactory
* [x] use Pin in ComponentInstance.getOutPins
* [x] add ComponentInstance.Id
* [ ] add Net.id
* [ ] clean model annotations

主要变化，

修改模型，去掉PinType、PinInstance，添加了Pin，更符合原书模型
把计算跳数的逻辑分成了：构建graph的应用逻辑+计算逻辑
应用了更多的类型，更多逻辑放到领域层