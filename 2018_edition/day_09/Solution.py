from collections import deque

# Game rules
player_count = 466
marble_count = 7143600
magic_number = 23

# Initialize stuff
cur_player = 0
player_to_score = {}
for id in range(0, player_count):
    player_to_score[id] = 0
dq = deque([0])

for i in range(1, marble_count+1):
    if i % 23 == 0:
        dq.rotate(7)
        player_to_score[cur_player] += i + dq.pop()
        dq.rotate(-1)
    else:
        dq.rotate(-1)
        dq.append(i)
    cur_player = (cur_player + 1) % player_count

print(max(player_to_score.values()))
