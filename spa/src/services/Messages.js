const Messages = {
    getMessage:  async (id) => {
        const options = {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        };
        try {
            const response = await fetch(`http://localhost:8080/messages/` + id, options)
            const json = await response.json();
            // console.log(json)
            return json
        } catch (err) {
            console.log('Error getting message', err)
        }
    },

    getMessages : async (type) => {
        const options = {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        };
        try {
            const response = await fetch(`http://localhost:8080/messages/${type}`, options)
            const json = await response.json();
            // console.log(json)
            return json
        } catch (err) {
            console.log('Error getting documents', err)
        }
    },

    postMessage : async (message) => {
        const options = {
            method: 'POST',
            body: JSON.stringify(message),
            credentials: 'include',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };
        fetch(`http://localhost:8080/messages`, options)
            .then(response => response.json())
            .then(response => console.log('Success:', JSON.stringify(response)))
            .catch(error => console.error('Error:', error))
            .then(response => location.href="/");
    }
}

export default Messages