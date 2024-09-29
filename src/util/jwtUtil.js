import axios from "axios";
import {getCookie, setCookie} from "./cookieUtil";
import {API_SERVER_HOST} from "../api/todoApi";

const jwtAxios = axios.create();

const refreshJWT =  async (accessToken, refreshToken) => {

	const host = API_SERVER_HOST

	const header = {headers: {"Authorization":`Bearer ${accessToken}`}}

	const res = await axios.get(`${host}/api/member/refresh?refreshToken=${refreshToken}`, header)

	console.log("----------------------")
	console.log(res.data)

	return res.data
}



const beforeReq = (config) => {
	console.log("before request.............")

	const memberInfo = getCookie("member")

	if( !memberInfo ) {
		console.log("Member NOT FOUND")
		return Promise.reject(
				{response:
							{data:
										{error:"REQUIRE_LOGIN"}
							}
				}
		)
	}

	const {accessToken} = memberInfo

	// Authorization 헤더 처리
	config.headers.Authorization = `Bearer ${accessToken}`

	return config
}

const requestFail = (err) => {

	console.log("request error.......")

	return Promise.reject(err)


}

const beforeRes = async (res) => {

	console.log("before return response...........")

	const data = res.data

	console.log("DATA")
	console.log(data)

	if(data && data.error === 'ERROR_ACCESS_TOKEN'){

		const memberCookieValue = getCookie('member');

		console.log("멤버쿠키")
		console.log(memberCookieValue)

		const result = await refreshJWT(memberCookieValue.accessToken, memberCookieValue.refreshToken)

		console.log("새로운 쿠키값")
		console.log(result)

		// 새로운 억세스, 리프레쉬 토큰이 온다

		memberCookieValue.accessToken = result.accessToken
		memberCookieValue.refreshToken = result.refreshToken

		// setCookie('member',JSON.stringify(memberCookieValue), 1)

		const origianlRequest = res.config
		origianlRequest.headers.Authorization = `Bearer ${memberCookieValue.accessToken}`

		return await axios(origianlRequest)
	}

	return res;
}


const responseFail = (err) => {
	console.log("response fail error.............")
	return Promise.reject(err);
}


jwtAxios.interceptors.request.use(beforeReq, requestFail)
jwtAxios.interceptors.response.use(beforeRes, responseFail)

export default jwtAxios;
