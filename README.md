![123](https://github.com/user-attachments/assets/a6256292-33d9-4801-9b9d-78f11d9dea13)

## Bottle Note Batch Server

### 개요(Overview)

> Bottle note 프로젝트의 Batch 서버입니다.

```mermaid
flowchart LR
    B[module_api] --> D[module_core]
    B --> E[module_common]
    D --> E
    C[module_batch] --> D
    C --> E
```

module api

- 외부 클라이언트의 요청을 처리하고 응답을 반환합니다.
- 비즈니스 로직은 core 모듈에 위임하여 처리합니다.
- 배치의 트리거를 호출하고, 클라이언트에게 스케줄링 작업의 결과를 반환합니다.

module batch

- 주기적인 배치 작업을 처리합니다.
- 트리거 발동 시 core 모듈에서 필요한 정보를 가져옵니다.
- 가져온 정보를 가공하고, 필요한 작업을 수행한 후 결과를 core에 전달합니다.

module core

- 비즈니스 로직의 중심 모듈로, 데이터베이스와의 상호작용을 처리합니다.
- batch 모듈에서 가져온 데이터를 처리하고, 저장하거나 추가적인 분석을 수행합니다.
- 배치 작업이나 API 요청 시 필요한 데이터를 제공합니다.

module common

- api, batch, core에서 공통으로 사용하는 유틸리티, 상수, 예외 처리 등을 정의합니다.
- 프로젝트 전반에서 재사용되는 로직과 설정을 제공합니다.

![boton](https://github.com/user-attachments/assets/50bf121a-fb97-46d6-9d1f-6e56858363c1)
