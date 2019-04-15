import Utils        from '../../services/Utils.js'
import Messages        from '../../services/Messages.js'

let MessageShow = {

    render : async () => {
        let request = Utils.parseRequestURL()
        let message = await Messages.getMessage(request.id)
        
        return /*html*/`
            <div class="container">
                <h1>Message</h1>
                <br />
                <div class="message-view">
                    <div class="row">
                        <div class="col-sm-1 message-label">To:</div>
                        <div class="col-sm-4">${message.toId}"</div>
                    </div>
                    <div class="row">
                        <div class="col-sm-1 message-label">From:</div>
                        <div class="col-sm-4">${message.fromId}</div>
                    </div>
                    <div class="row">
                        <div class="col-sm-1 message-label">Subject:</div>
                        <div class="col-sm-5">${message.summary}</div>
                    </div>
                    <div class="row">
                        <div class="col-sm-1 message-label">Message:</div>
                        <div class="col-sm-7">${message.text}</div>
                    </div>
                    <div class="row">
                        <div class="col-sm-1 message-label">Created:</div>
                        <div class="col-sm-3">${message.created}</div>
                    </div>
                    <div class="row">
                        <div class="col-sm-offset-1 col-sm-3">
                            <a class="btn btn-primary" href="#/inbox">Done</a>
                        </div>
                    </div>
                </div>
            </div>
        `
    }
    , after_render: async () => {
    }
}

export default MessageShow;