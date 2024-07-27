# -*- mode: Python -*-
application_name = "sample-tilt"
port = 9082
application_class = 'br.com.sampletilt.SampleTiltApplicationKt'
project_name = 'til-dev-samples'
java_opts = '-Duser.timezone=America/Sao_Paulo -Dfile.encoding=UTF8 -Xms512m -Xmx1024m'
entrypoint_var = ['java', '-noverify', java_opts, '-cp', '.:./lib/*', application_class]

# Extensão de live update para sincronizar arquivos
# Para mais exemplos de extensões, acesse: https://docs.tilt.dev/extensions.html
load('ext://restart_process', 'docker_build_with_restart')

local_resource(
    application_name + '-compile',
    "./gradlew" + ' bootJar && ' +
    'unzip -o build/libs/*.jar -d build/jar-staging && ' +
    'rsync --inplace --checksum -r build/jar-staging/ build/jar',
    deps=['src', 'build.gradle'],
    labels=project_name + "-compile")

docker_build_with_restart(
    application_name + '-image',
    './build/jar',
    entrypoint=entrypoint_var,
    dockerfile='./Dockerfile',
    live_update=[
        sync('./build/jar/BOOT-INF/lib', '/app/lib'),
        sync('./build/jar/META-INF', '/app/META-INF'),
        sync('./build/jar/BOOT-INF/classes', '/app'),
    ],
)

k8s_yaml('./kub-tilt.yaml')
k8s_resource(application_name,
             resource_deps=[application_name + '-compile'], labels=project_name + "-pods", port_forwards=port)
