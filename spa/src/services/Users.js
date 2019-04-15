const Users = {
    getCurrentUser : async () => {
            const options = {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        };
        try {
            const response = await fetch('http://localhost:8080/user', options)
            const json = await response.json();
            // console.log(json)
            return json
        } catch (err) {
            console.log('Error getting documents', err)
        }
    },

    getUsers : async () => {
        const options = {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        };
        try {
            const response = await fetch('http://localhost:8080/users', options)
            const json = await response.json();
            // console.log(json)
            return json
        } catch (err) {
            console.log('Error getting documents', err)
        }
    }
}

export default Users