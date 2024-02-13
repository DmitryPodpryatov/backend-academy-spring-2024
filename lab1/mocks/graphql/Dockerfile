FROM node:18-alpine

WORKDIR /app

COPY ./package.json ./package.json
COPY ./schema.graphql ./schema.graphql
COPY ./gqlmocks.config.cjs ./gqlmocks.config.cjs

RUN npm install

CMD ["npx", "gqlmocks", "serve", "--config", "gqlmocks.config.cjs", "--fake"]
