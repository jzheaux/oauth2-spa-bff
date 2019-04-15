let ErrorMessage = {

    render : async (errorMessage) => {
        let view =  /*html*/`
            <div class="container">
                <h1>Error</h1>
                <br />
                <div style="font-size:large">${errorMessage}</div>
            </div>
        `
        return view
    }
    , after_render: async () => {
    }
}
export default ErrorMessage;