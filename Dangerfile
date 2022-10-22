message "Thanks @#{github.pr_author}!"

if github.pr_body.length == 0
    fail "Please provide a summary in the PR, bro!"
end

if git.lines_of_code > 500
    warn "PR too big"
end
