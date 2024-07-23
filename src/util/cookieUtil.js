import {Cookies} from "react-cookie";

const cookies = new Cookies()

export const setCookie = (name, value, days = 1) => {
	const expiers = new Date();
	expiers.setUTCDate(expiers.getUTCDate() + days) // 보관기간


	// 하위 경로에서 전부 사용하므로 path는 '/'로 설정
	return cookies.set(name,value,{expires : expiers, path : '/'})
}

export const getCookie = (name) => {
	return cookies.get(name);
}

export const removeCookie = (name, path = '/') => {
	cookies.remove(name, {path:path})
}
