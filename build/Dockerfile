FROM maven:3.6.3-jdk-8
COPY ./install-node.sh /root
COPY ./build.sh /root
RUN /root/install-node.sh
RUN curl -L https://github.com/AbsaOSS/spline/archive/release/0.4.0.zip --output /tmp/spline.zip
RUN unzip /tmp/spline.zip -d /
WORKDIR /spline-release-0.4.0
RUN /root/build.sh
