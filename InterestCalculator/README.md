## 利息计算
本套代码为9.1 章概念挖掘中提到的案例:
故事以一家假想的金融公司为背景，该公司经营商业贷款和其他一些生息资产。公司开发了一个用于跟踪这些投资及收益的应用程序，通过一项一项地添加功能来使它不断地发展。每天晚上，公司都会运行一个批处理脚本，用于计算当天所生成的利息和费用，并把它们相应地记录到公司的财务软件中。
模型经过了如下迭代
V0
![ddd-9.1.2-利息模型-0.1.drawio.png](./src/main/resources/model/ddd-9.1.2-%E5%88%A9%E6%81%AF%E6%A8%A1%E5%9E%8B-0.1.drawio.png)
开始需求是每天计算一遍每笔资产对应的手续费和利息，最初识别到的模型就是资产和收益还有手续费。
代码如V0包下的代码所示

V1
![ddd-9.1.2-利息模型-V1.0.drawio.png](./src/main/resources/model/ddd-9.1.2-%E5%88%A9%E6%81%AF%E6%A8%A1%E5%9E%8B-V1.0.drawio.png)
V2
![ddd-9.1.2-利息模型-V1.1.drawio.png](./src/main/resources/model/ddd-9.1.2-%E5%88%A9%E6%81%AF%E6%A8%A1%E5%9E%8B-V1.1.drawio.png)
V3
![ddd-9.1.2-利息模型-V2.0.drawio.png](./src/main/resources/model/ddd-9.1.2-%E5%88%A9%E6%81%AF%E6%A8%A1%E5%9E%8B-V2.0.drawio.png)