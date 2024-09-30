import {atom, selector} from "recoil";


export const cartState = atom({

	key : 'cartState',
	default : [],



})

export const cartTotalState = selector({
	key : 'cartTotalState',

	get : ( {get} ) => {

		const arr = get(cartState)

		console.log("배열")
		console.log(arr)

		const initalValue = 0

		return arr.reduce((total, current) => total + current.price * current.qty , initalValue)

	}

})