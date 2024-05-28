# Семинар 14. Distributed tracing. Масштабирование.

### Trace vs Span

![](https://www.jaegertracing.io/img/spans-traces.png)

Для начала вспомним, что такое trace и span.

- https://logit.io/blog/post/traces-vs-spans/

### Distributed Tracing. Jaeger

Jaeger (ягер) — это распределенная платформа трассировки. С помощью Jaeger мы можем отслеживать флоу запросов и находить
уязвимости в производительности системы.

- https://www.jaegertracing.io/docs/1.57/
- https://www.jaegertracing.io/docs/1.23/architecture/

На этом семинаре мы разберемся с функционалом Jaeger через готовый туториал _All in One_:

- https://www.jaegertracing.io/docs/1.23/getting-started/
- https://medium.com/opentracing/take-opentracing-for-a-hotrod-ride-f6e3141f7941

### Масштабирование

#### [пропускаем] docker-compose &rarr; swarm mode

- https://docs.docker.com/engine/swarm/
- https://docs.docker.com/engine/swarm/swarm-tutorial/

Либо более ручной подход — несколько копий сервисов, к которым load-balancer распределяет трафик

- аналогично подходу, описанному [здесь](https://dev.to/mazenr/how-to-implement-a-load-balancer-using-nginx-docker-4g73)

#### minikube

- https://minikube.sigs.k8s.io/docs/start/
- https://minikube.sigs.k8s.io/docs/tutorials/kubernetes_101/module5/

### Стратегии деплоя

- Recreate
- Blue-Green
- Rolling update
- Canary
- A/B testing
- Shadow

---

- https://thenewstack.io/deployment-strategies/
- https://docs.openshift.com/dedicated/3/dev_guide/deployments/deployment_strategies.html
- https://minikube.sigs.k8s.io/docs/tutorials/kubernetes_101/module6/
