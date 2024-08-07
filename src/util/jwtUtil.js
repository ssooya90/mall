import axios from "axios";
import {getCookie, setCookie} from "./cookieUtil";
import {API_SERVER_HOST} from "../api/todoApi";


const jwtAxios = axios.create();

const refreshJWT = async (accessToken, refreshToken) => {

	const host = API_SERVER_HOST

	const header = {headers : {'Authorization' : `Bearer ${accessToken}`}}

	const res = await axios.get(`${host}/api/member/refresh?refreshToken=${refreshToken}`,header)

	// res data는 새로 만들어진 access 토큰과 refresh 토큰임
	console.log(res.data)

	return res.data;

}


const beforeReq = (config) => {

	console.log(config)
	console.log("beofre request....")

	const memberInfo = getCookie('member')

	if(!memberInfo){

		console.log("MEMBER NOT FOUND")

		return Promise.reject({
			response: {
				data : {
					error : "REQUIRE_LOGIN"
				}
			}
		})
	}

	const {accessToken} = memberInfo

	console.log("------------------" + accessToken)

	config.headers.Authorization = `Bearer ${accessToken}`

	return config
}

const requestFail = (err) => {

	console.log("request error.......")

	return Promise.reject(err)


}

const beforeRes = async (res) => {

	console.log("before return response")

	const data = res.date

	if(data && data.error == 'ERROR_ACCESS_TOKEN'){

		const memberCookieValue = getCookie('member');

		const result = await refreshJWT(memberCookieValue.accessToken, memberCookieValue.refreshToken)

		// 새로운 억세스, 리프레쉬 토큰이 온다

		memberCookieValue.accessToken = result.accessToken
		memberCookieValue.refreshToken = result.refreshToken

		setCookie('member',JSON.stringify(memberCookieValue), 1)

		const origianlRequest = res.config
		origianlRequest.headers.Authorization = `Bearer ${memberCookieValue.accessToken}`

		return await axios(origianlRequest)
	}

	return res;
}


const responseFail = (err) => {

	console.log("response error.......")

	return Promise.reject(err)


}


jwtAxios.interceptors.request.use(beforeReq, requestFail)
jwtAxios.interceptors.response.use(beforeRes, responseFail)

export default jwtAxios;
