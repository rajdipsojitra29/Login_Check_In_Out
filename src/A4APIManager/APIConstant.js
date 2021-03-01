const SERVER_DOMAIN = 'dev.one-fm.com'

const PROTOCOL = 'https'
const ROOT_PATH = 'api/method'












export default {
    domain: SERVER_DOMAIN,
    port: null,
    host: `${PROTOCOL}://${SERVER_DOMAIN}`,
    baseURL: `${PROTOCOL}://${SERVER_DOMAIN}/${ROOT_PATH}/`,
};