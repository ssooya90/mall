import {Cookies} from "react-cookie";

const cookies = new Cookies()

export const setCookie = (name, value, days = 1) => {

	const expires = new Date()
	expires.setUTCDate(expires.getUTCDate() + days ) //보관기한


	// 하위 경로에서 전부 사용하므로 path는 '/'로 설정
	return cookies.set(name, value, {path:'/', expires:expires})
}

export const getCookie = (name) => {
	return cookies.get(name)
}

export const removeCookie = (name, path = '/') => {
	cookies.remove(name, {path:path})
}
